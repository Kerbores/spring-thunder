package club.zhcs.thunder.controller.admin.apm;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.titans.utils.db.Result;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project app
 *
 * @file DruidModule.java
 *
 * @description 数据库
 *
 * @time 2016年3月15日 下午5:18:55
 *
 */
@Controller
@RequestMapping("db")
public class DruidController extends BaseController {

	@RequestMapping("connectionPool")
	@RequiresRoles("admin")
	public String connectionPool(String id, Model model) {
		model.addAttribute("obj", Result.success().addData("id", id));
		return "pages/db/connectionPool";
	}

	@RequestMapping("dashboard")
	@RequiresRoles("admin")
	public String dashboard(Model model) {
		model.addAttribute("obj", Result.success().setTitle("Druid 监控"));
		return "pages/db/dashboard";
	}

	@RequestMapping("sessionDetail")
	public String sessionDetail(String sessionId, Model model) {
		model.addAttribute("obj", Result.success().addData("sessionId", sessionId));
		return "pages/db/sessionDetail";
	}

	@RequestMapping("sqlDetail")
	public String sqlDetail(int sqlId, Model model) {
		model.addAttribute("obj", Result.success().addData("sqlId", sqlId));
		return "pages/db/sqlDetail";
	}

	@RequestMapping("uriDetail")
	public String uriDetail(String uri, Model model) {
		model.addAttribute("obj", Result.success().addData("uri", uri));
		return "pages/db/uriDetail";
	}

}