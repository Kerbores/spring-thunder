package club.zhcs.thunder.controller.admin.apm;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hyperic.sigar.SigarException;
import org.nutz.dao.Cnd;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import club.zhcs.thunder.biz.apm.APMAlarmService;
import club.zhcs.thunder.biz.config.ConfigService;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.thunder.domain.apm.APMAlarm;
import club.zhcs.thunder.domain.config.Config;
import club.zhcs.titans.gather.Gathers;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

/**
 * 
 * @author kerbores
 *
 * @email kerbores@gmail.com
 *
 */
@Controller
@RequestMapping("apm")
public class APMController extends BaseController {

	@Autowired
	APMAlarmService apmAlarmService;

	@Autowired
	PropertiesProxy config;

	@Autowired
	ConfigService configService;

	@RequestMapping("alarm")
	public @ResponseBody Pager<APMAlarm> alarm() {
		return apmAlarmService.searchByPage(1, 5, null);
	}

	@RequestMapping("dashboard")
	@RequiresRoles("admin")
	public String dashboard(Model model) throws SigarException {
		model.addAttribute("obj", Result.success().addData(Gathers.all()).addData("config", config).setTitle("本机状态"));
		return "pages/apm/dashboard";
	}

	@RequestMapping("/detail/*")
	public @ResponseBody APMAlarm detail(String code) {
		return apmAlarmService.fetch(code);
	}

	@RequestMapping("list")
	@RequiresRoles("admin")
	public String list(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		Pager<APMAlarm> pager = apmAlarmService.searchByPage(_fixPage(page));
		pager.setUrl(_base() + "/apm/list");
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle("告警列表"));
		return "pages/apm/list";
	}

	@RequestMapping("search")
	@RequiresRoles("admin")
	public String search(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam("key") String key, Model model) {
		Pager<APMAlarm> pager = apmAlarmService.searchByKeyAndPage(_fixSearchKey(key), _fixPage(page), "type", "ip");
		pager.setUrl(_base() + "/apm/search");
		pager.addParas("key", key);
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle("告警列表"));
		return "pages/apm/list";
	}

	@RequestMapping("setting")
	@RequiresRoles("admin")
	public @ResponseBody Result setting(@RequestParam("type") String type, @RequestParam("types") String types, @RequestParam("percent") String percent) {
		NutMap data = NutMap.NEW();
		data.put(type + ".alarm.percent", percent);
		data.put(type + ".alarm.types", types);

		config.putAll(data);// 更新内存

		Config pConfig = configService.fetch(Cnd.where("name", "=", type + ".alarm.percent"));
		Config tConfig = configService.fetch(Cnd.where("name", "=", type + ".alarm.types"));
		if (pConfig == null) {
			pConfig = new Config();
			pConfig.setName(type + ".alarm.percent");
			configService.save(pConfig);
		}
		pConfig.setValue(percent);
		if (tConfig == null) {
			tConfig = new Config();
			tConfig.setName(type + ".alarm.types");
			configService.save(tConfig);
		}
		tConfig.setValue(types);

		return configService.update(tConfig) == 1 && configService.update(pConfig) == 1 ? Result.success() : Result.fail("配置失败!"); // 更新数据库
	}

}
