package club.zhcs.thunder.controller.admin;

import org.nutz.dao.Cnd;
import org.nutz.ioc.impl.PropertiesProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import club.zhcs.thunder.biz.config.ConfigService;
import club.zhcs.thunder.controller.HomeController;
import club.zhcs.thunder.domain.InstallPermission;
import club.zhcs.thunder.domain.config.Config;
import club.zhcs.thunder.ext.shiro.anno.ThunderRequiresPermissions;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

/**
 * 
 * @author admin
 *
 * @email kerbores@gmail.com
 *
 */
@Controller
@RequestMapping("config")
public class ConfigController extends HomeController {

	@Autowired
	ConfigService configService;

	@Autowired
	PropertiesProxy config;

	@RequestMapping(value = "add", method = RequestMethod.GET)
	@ThunderRequiresPermissions(InstallPermission.CONFIG_ADD)
	public String add(Model model) {
		model.addAttribute("obj", Result.success().setTitle("添加配置"));
		return "pages/config/add_edit";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ThunderRequiresPermissions(InstallPermission.CONFIG_ADD)
	public @ResponseBody Result add(Config config) {
		this.config.put(config.getName(), config.getValue());
		return configService.save(config) == null ? Result.fail("添加配置失败") : Result.success();
	}

	@RequestMapping("delete")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_DELETE)
	public @ResponseBody Result delete(@RequestParam("id") String key) {
		this.config.remove(key);
		return configService.delete(key) == 1 ? Result.success() : Result.fail("删除配置失败!");
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ThunderRequiresPermissions(InstallPermission.CONFIG_EDIT)
	public @ResponseBody Result edit(Config config) {
		this.config.put(config.getName(), config.getValue());
		return configService.update(config) == 1 ? Result.success() : Result.fail("更新失败!");
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	@ThunderRequiresPermissions(InstallPermission.CONFIG_EDIT)
	public String edit(@RequestParam("key") String key, Model model) {
		model.addAttribute("obj",
				Result.success().addData("config", configService.fetch(Cnd.where("name", "=", key))).addData("key", key).addData("value", config.get(key)).setTitle("编辑配置"));
		return "beetl:pages/config/add_edit";
	}

	@RequestMapping("list")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_LIST)
	public String list(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		page = _fixPage(page);
		Pager<Config> pager = configService.searchByPage(page);
		pager.setUrl(_base() + "/config/list");
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle("配置列表"));
		return "pages/config/list";
	}

	@RequestMapping("search")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_LIST)
	public String search(@RequestParam("key") String key, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		page = _fixPage(page);
		key = _fixSearchKey(key);
		Pager<Config> pager = configService.searchByKeyAndPage(key, page, "name", "value", "description");
		pager.setUrl(_base() + "/config/search");
		pager.addParas("key", key);
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle("配置检索"));
		return "pages/config/list";
	}

}
