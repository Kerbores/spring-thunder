package club.zhcs.thunder.controller.admin.acl;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.Ok;

import club.zhcs.thunder.Thunder;
import club.zhcs.thunder.biz.acl.UserService;
import club.zhcs.thunder.domain.acl.User;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Result;

@At("setting")
public class SettingModule extends AbstractBaseModule {

	@Inject
	UserService userService;

	@At
	@Ok("beetl:pages/admin/auth/user/detail.html")
	public Result profile(@Attr(Thunder.SessionKeys.USER_KEY) User user) {
		return Result.success().setTitle("个人信息").addData("user", userService.fetch(user.getId()));
	}
}
