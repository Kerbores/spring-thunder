package club.zhcs.thunder.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import club.zhcs.thunder.Thunder.SessionKeys;
import club.zhcs.thunder.aop.SystemLog;
import club.zhcs.thunder.biz.acl.ShiroUserService;
import club.zhcs.thunder.biz.acl.UserService;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.thunder.domain.acl.User;
import club.zhcs.titans.nutz.captcha.JPEGView;
import club.zhcs.titans.utils.codec.DES;
import club.zhcs.titans.utils.db.Result;

/**
 * @author admin
 *
 * @email kerbores@gmail.com
 *
 */
@Controller
@RequestMapping("system")
public class SystemController extends BaseController {

	@Autowired
	ShiroUserService shiroUserService;

	@Autowired
	UserService userService;

	@RequestMapping("login")
	@SystemLog(module = "系统", methods = "登录")
	public @ResponseBody Result login(@RequestParam("user") String user, @RequestParam("password") String password, @RequestParam("captcha") String captcha,
			@RequestParam("rememberMe") boolean rememberMe, HttpSession session) {
		if (Strings.equalsIgnoreCase(captcha, session.getAttribute(JPEGView.CAPTCHA).toString())) {
			Result result = shiroUserService.login(user, password);
			if (result.isSuccess()) {
				// 登录成功处理
				_putSession(SessionKeys.USER_KEY, result.getData().get("loginUser"));
				if (rememberMe) {
					NutMap data = NutMap.NEW();
					data.put("user", user);
					data.put("password", password);
					data.put("rememberMe", rememberMe);
					_addCookie("kerbores", DES.encrypt(Json.toJson(data)), 24 * 60 * 60 * 365);
				}
			}
			return result;
		} else {
			return Result.fail("验证码输入错误");
		}
	}

	@RequiresAuthentication
	@RequestMapping("/main")
	public String main(@SessionAttribute(name = SessionKeys.USER_KEY, required = false) User user, Model model) {
		model.addAttribute("obj", Result.success().setTitle("项目说明"));
		return "pages/admin/main";
	}
}
