package club.zhcs.thunder.controller.admin.log;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import club.zhcs.thunder.biz.log.LoginLogService;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.thunder.domain.log.LoginLog;
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
@RequestMapping("login")
public class LoginLogController extends BaseController {

	@Autowired
	LoginLogService loginLogService;

	@RequestMapping("list")
	@RequiresRoles("admin")
	public String list(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		Pager<LoginLog> pager = loginLogService.searchByPage(_fixPage(page));
		pager.setUrl(_base() + "/login/list");
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle(" 登录日志列表"));
		return "pages/log/login/list";
	}

	@RequestMapping("search")
	@RequiresRoles("admin")
	public String search(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam("key") String key, Model model) {
		Pager<LoginLog> pager = loginLogService.searchByKeyAndPage(_fixSearchKey(key), _fixPage(page), "account", "ip");
		pager.setUrl(_base() + "/login/search");
		pager.addParas("key", key);
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle(" 登录日志列表"));
		return "pages/log/login/list";
	}

}
