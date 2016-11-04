package club.zhcs.thunder.controller.admin;

import org.nutz.dao.Cnd;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import club.zhcs.thunder.biz.config.ConfigService;
import club.zhcs.thunder.domain.InstallPermission;
import club.zhcs.thunder.domain.config.Config;
import club.zhcs.thunder.ext.shiro.anno.ThunderRequiresPermissions;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

/**
 * 
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project thunder-web
 *
 * @file configModule.java
 *
 * @description 配置管理
 *
 * @copyright 内部代码,禁止转发
 *
 * @time 2016年5月12日 上午11:28:24
 *
 */
@At("config")
public class ConfigModule extends AbstractBaseModule {

	@Inject
	ConfigService configService;

	@Inject("config")
	PropertiesProxy config;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kerbores.nutz.module.base.AbstractBaseModule#_getNameSpace()
	 */
	@Override
	public String _getNameSpace() {
		return "settings";
	}

	@At
	@GET
	@Ok("beetl:pages/config/add_edit.html")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_ADD)
	public Result add() {
		return Result.success().setTitle("添加配置");
	}

	@At
	@POST
	@ThunderRequiresPermissions(InstallPermission.CONFIG_ADD)
	public Result add(@Param("..") Config config) {
		this.config.put(config.getName(), config.getValue());
		return configService.save(config) == null ? Result.fail("添加配置失败") : Result.success();
	}

	@At
	@ThunderRequiresPermissions(InstallPermission.CONFIG_DELETE)
	public Result delete(@Param("id") String key) {
		this.config.remove(key);
		return configService.delete(key) == 1 ? Result.success() : Result.fail("删除配置失败!");
	}

	@At
	@POST
	@ThunderRequiresPermissions(InstallPermission.CONFIG_EDIT)
	public Result edit(@Param("..") Config config) {
		this.config.put(config.getName(), config.getValue());
		return configService.update(config) == 1 ? Result.success() : Result.fail("更新失败!");
	}

	@At("/edit")
	@GET
	@Ok("beetl:pages/config/add_edit.html")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_EDIT)
	public Result edit(@Param("key") String key) {
		return Result.success().addData("config", configService.fetch(Cnd.where("name", "=", key))).addData("key", key).addData("value", config.get(key)).setTitle("编辑配置");
	}

	@At
	@Ok("beetl:pages/config/list.html")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_LIST)
	public Result list(@Param(value = "page", df = "1") int page) {
		page = _fixPage(page);
		Pager<Config> pager = configService.searchByPage(page);
		pager.setUrl(_base() + "/config/list");
		return Result.success().addData("pager", pager).setTitle("配置列表");
	}

	@At
	@Ok("beetl:pages/config/list.html")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_LIST)
	public Result search(@Param("key") String key, @Param(value = "page", df = "1") int page) {
		page = _fixPage(page);
		key = _fixSearchKey(key);
		Pager<Config> pager = configService.searchByKeyAndPage(key, page, "name", "value", "description");
		pager.setUrl(_base() + "/config/search");
		pager.addParas("key", key);
		return Result.success().addData("pager", pager).setTitle("配置检索");
	}

}
