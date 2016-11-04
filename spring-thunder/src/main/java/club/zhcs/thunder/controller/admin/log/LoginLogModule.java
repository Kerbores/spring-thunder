package club.zhcs.thunder.controller.admin.log;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import club.zhcs.thunder.biz.log.LoginLogService;
import club.zhcs.thunder.domain.log.LoginLog;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project app
 *
 * @file LoginLogModule.java
 *
 * @description // TODO write some description of this class
 *
 * @time 2016年3月15日 下午5:21:30
 *
 */
@At("login")
public class LoginLogModule extends AbstractBaseModule {

	@Inject
	LoginLogService loginLogService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kerbores.nutz.module.base.AbstractBaseModule#_getNameSpace()
	 */
	@Override
	public String _getNameSpace() {
		return "log";
	}

	@At
	@Ok("beetl:pages/log/login/list.html")
	@RequiresRoles("admin")
	public Result list(@Param(value = "page", df = "1") int page) {
		Pager<LoginLog> pager = loginLogService.searchByPage(_fixPage(page));
		pager.setUrl(_base() + "/login/list");
		return Result.success().addData("pager", pager).setTitle(" 登录日志列表");
	}

	@At
	@Ok("beetl:pages/log/login/list.html")
	@RequiresRoles("admin")
	public Result search(@Param(value = "page", df = "1") int page, @Param("key") String key) {
		Pager<LoginLog> pager = loginLogService.searchByKeyAndPage(_fixSearchKey(key), _fixPage(page), "account", "ip");
		pager.setUrl(_base() + "/login/search");
		pager.addParas("key", key);
		return Result.success().addData("pager", pager).setTitle(" 登录日志列表");
	}

}
