package club.zhcs.thunder.controller.admin.acl;

import org.nutz.mvc.annotation.Ok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import club.zhcs.thunder.biz.acl.PermissionService;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.thunder.domain.InstallPermission;
import club.zhcs.thunder.domain.acl.Permission;
import club.zhcs.thunder.ext.shiro.anno.ThunderRequiresPermissions;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

@Controller
@RequestMapping("permission")
public class PermissionController extends BaseController {

	@Autowired
	private PermissionService permissionService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 *
	 * @author 王贵源
	 */
	@RequestMapping("list")
	@Ok("beetl:pages/admin/auth/permission/list.html")
	@ThunderRequiresPermissions(InstallPermission.PERMISSION_LIST)
	public String list(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		page = _fixPage(page);
		Pager<Permission> pager = permissionService.searchByPage(page);
		pager.setUrl(_base() + "/permission/list");
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle("权限列表"));
		return "pages/admin/auth/permission/list";
	}

	/**
	 * 搜索
	 * 
	 * @param page
	 * @param key
	 * @return
	 */
	@RequestMapping("search")
	@Ok("beetl:pages/admin/auth/permission/list.html")
	@ThunderRequiresPermissions(InstallPermission.PERMISSION_LIST)
	public String search(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam("key") String key, Model model) {
		page = _fixPage(page);
		key = _fixSearchKey(key);
		Pager<Permission> pager = permissionService.searchByKeyAndPage(key, page, "name", "description");
		pager.setUrl(_base() + "/permission/search");
		pager.addParas("key", key);
		model.addAttribute("obj", Result.success().addData("pager", pager).addData("key", key).setTitle("权限检索"));
		return "pages/admin/auth/permission/list";
	}

}
