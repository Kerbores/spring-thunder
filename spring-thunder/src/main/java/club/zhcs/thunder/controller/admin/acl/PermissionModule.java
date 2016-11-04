package club.zhcs.thunder.controller.admin.acl;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import club.zhcs.thunder.biz.acl.PermissionService;
import club.zhcs.thunder.domain.InstallPermission;
import club.zhcs.thunder.domain.acl.Permission;
import club.zhcs.thunder.ext.shiro.anno.ThunderRequiresPermissions;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 权限控制器
 * 
 * @copyright 内部代码,禁止转发
 *
 *
 * @time 2016年1月26日 下午3:37:37
 */
@At("permission")
public class PermissionModule extends AbstractBaseModule {

	@Inject
	private PermissionService permissionService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dgj.nutz.module.base.AbstractBaseModule#_getNameSpace()
	 */
	@Override
	public String _getNameSpace() {
		return "acl";
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 *
	 * @author 王贵源
	 */
	@At
	@Ok("beetl:pages/admin/auth/permission/list.html")
	@ThunderRequiresPermissions(InstallPermission.PERMISSION_LIST)
	public Result list(@Param(value = "page", df = "1") int page) {
		page = _fixPage(page);
		Pager<Permission> pager = permissionService.searchByPage(page);
		pager.setUrl(_base() + "/permission/list");
		return Result.success().addData("pager", pager).setTitle("权限列表");
	}

	/**
	 * 搜索
	 * 
	 * @param page
	 * @param key
	 * @return
	 */
	@At
	@Ok("beetl:pages/admin/auth/permission/list.html")
	@ThunderRequiresPermissions(InstallPermission.PERMISSION_LIST)
	public Result search(@Param(value = "page", df = "1") int page, @Param("key") String key) {
		page = _fixPage(page);
		key = _fixSearchKey(key);
		Pager<Permission> pager = permissionService.searchByKeyAndPage(key, page, "name", "description");
		pager.setUrl(_base() + "/permission/search");
		pager.addParas("key", key);
		return Result.success().addData("pager", pager).addData("key", key).setTitle("权限检索");
	}

}
