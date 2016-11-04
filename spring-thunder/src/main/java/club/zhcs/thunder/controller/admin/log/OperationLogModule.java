package club.zhcs.thunder.controller.admin.log;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import club.zhcs.thunder.biz.log.OperationLogService;
import club.zhcs.thunder.domain.log.OperationLog;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project app
 *
 * @file OperationLogModule.java
 *
 * @description
 *
 * @time 2016年3月15日 下午5:21:08
 *
 */
@At("operation")
public class OperationLogModule extends AbstractBaseModule {

	@Inject
	OperationLogService operationLogService;

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
	@Ok("beetl:pages/log/operation/list.html")
	@RequiresRoles("admin")
	public Result list(@Param(value = "page", df = "1") int page) {
		Pager<OperationLog> pager = operationLogService.searchByPage(_fixPage(page));
		pager.setUrl(_base() + "/operation/list");
		return Result.success().addData("pager", pager).setTitle("操作日志列表");
	}

	@At
	@Ok("beetl:pages/log/operation/list.html")
	@RequiresRoles("admin")
	public Result search(@Param(value = "page", df = "1") int page, @Param("key") String key) {
		Pager<OperationLog> pager = operationLogService.searchByKeyAndPage(_fixSearchKey(key), _fixPage(page), "account", "ip", "module", "action", "description");
		pager.setUrl(_base() + "/operation/list");
		pager.addParas("key", key);
		return Result.success().addData("pager", pager).setTitle("操作日志列表");
	}

}
