package club.zhcs.thunder.controller.admin.acl;

import org.nutz.mvc.annotation.Ok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import club.zhcs.thunder.Thunder;
import club.zhcs.thunder.biz.acl.UserService;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.thunder.domain.acl.User;
import club.zhcs.titans.utils.db.Result;

@Controller
@RequestMapping("setting")
public class SettingController extends BaseController {

	@Autowired
	UserService userService;

	@RequestMapping("profile")
	@Ok("beetl:pages/admin/auth/user/detail.html")
	public String profile(@SessionAttribute(Thunder.SessionKeys.USER_KEY) User user, Model model) {
		model.addAttribute("obj", Result.success().setTitle("个人信息").addData("user", userService.fetch(user.getId())));
		return "pages/admin/auth/user/detail";
	}
}
