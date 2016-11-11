package club.zhcs.thunder.controller.admin.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import club.zhcs.thunder.biz.acl.RoleService;
import club.zhcs.thunder.controller.HomeController;
import club.zhcs.thunder.domain.InstallPermission;
import club.zhcs.thunder.domain.acl.Role;
import club.zhcs.thunder.ext.shiro.anno.ThunderRequiresPermissions;
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
@RequestMapping("role")
public class RoleController extends HomeController {

	@Autowired
	private RoleService roleService;

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
	 * 添加角色页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	@ThunderRequiresPermissions(InstallPermission.ROLE_ADD)
	public String add(Model model) {
		model.addAttribute("obj", Result.success().setTitle("添加角色"));
		return "pages/admin/auth/role/add_edit";
	}

	/**
	 * 添加角色
	 * 
	 * @param role
	 *            待添加角色
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ThunderRequiresPermissions(InstallPermission.ROLE_ADD)
	public @ResponseBody Result add(Role role) {
		if (null != roleService.fetch(role.getName())) {
			return Result.fail("角色" + role.getName() + "已存在");
		}
		role.setInstalled(false);
		role = roleService.save(role);
		return role == null ? Result.fail("添加角色失败") : Result.success().addData("role", role);
	}

	/**
	 * 删除角色
	 * 
	 * @param id
	 *            角色id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ThunderRequiresPermissions(InstallPermission.ROLE_DELETE)
	public @ResponseBody Result delete(@PathVariable("id") int id) {
		return roleService.delete(id) == 1 ? Result.success() : Result.fail("删除失败!");
	}

	/**
	 * 编辑页码页面
	 * 
	 * @param id
	 *            角色id
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	@ThunderRequiresPermissions(InstallPermission.ROLE_EDIT)
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("obj", Result.success().addData("role", roleService.fetch(id)).setTitle("编辑角色"));
		return "pages/admin/auth/role/add_edit";
	}

	/**
	 * 授权页面
	 * 
	 * @param id
	 * @return
	 *
	 * @author 王贵源
	 */
	@RequestMapping(value = "/grant/{id}", method = RequestMethod.GET)
	@ThunderRequiresPermissions(InstallPermission.ROLE_GRANT)
	public String grant(@PathVariable("id") int id, Model model) {
		model.addAttribute("obj", Result.success().addData("records", roleService.findPermissionsWithRolePowerdInfoByRoleId(id)).addData("roleId", id).setTitle("角色授权"));
		return "pages/admin/auth/role/grant";
	}

	/**
	 * ajax 授权
	 *
	 * @param ids
	 * @param roleId
	 * @return
	 *
	 * @author 王贵源
	 */
	@RequestMapping(value = "grant", method = RequestMethod.POST)
	@ThunderRequiresPermissions(InstallPermission.ROLE_GRANT)
	public @ResponseBody Result grant(@RequestParam(value = "permissions", defaultValue = "") int[] ids, @RequestParam("id") int roleId) {
		return roleService.setPermission(ids, roleId);
	}

	/**
	 * 角色列表
	 * 
	 * @param page
	 *            页码
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ThunderRequiresPermissions(InstallPermission.ROLE_LIST)
	public String list(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		page = _fixPage(page);
		Pager<Role> pager = roleService.searchByPage(page);
		pager.setUrl(_base() + "/role/list");
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle("角色列表"));
		return "pages/admin/auth/role/list";
	}

	/**
	 * 搜索角色
	 * 
	 * @param page
	 *            页码
	 * @param key
	 *            关键词
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	@ThunderRequiresPermissions(InstallPermission.ROLE_LIST)
	public String search(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam("key") String key, Model model) {
		page = _fixPage(page);
		key = _fixSearchKey(key);
		Pager<Role> pager = roleService.searchByKeyAndPage(key, page, "name", "description");
		pager.setUrl(_base() + "/role/search");
		pager.addParas("key", key);
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle("角色检索"));
		return "pages/admin/auth/role/list";
	}

	/**
	 * 更新角色
	 * 
	 * @param role
	 *            待更新角色
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ThunderRequiresPermissions(InstallPermission.ROLE_EDIT)
	public @ResponseBody Result update(Role role) {
		return roleService.update(role, "description") == 1 ? Result.success() : Result.fail("更新失败!");
	}
}
