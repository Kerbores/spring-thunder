package club.zhcs.thunder.controller.admin.acl;

import java.util.List;

import org.nutz.dao.entity.Record;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.Ok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import club.zhcs.thunder.aop.SystemLog;
import club.zhcs.thunder.biz.acl.UserService;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.thunder.domain.InstallPermission;
import club.zhcs.thunder.domain.acl.User;
import club.zhcs.thunder.ext.shiro.anno.ThunderRequiresPermissions;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project thunder-web
 *
 * @file UserModule.java
 *
 * @description 用户管理
 *
 * @time 2016年3月8日 上午10:51:26
 *
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

	@Autowired
	UserService userService;

	/**
	 * 添加用户页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	@ThunderRequiresPermissions(InstallPermission.USER_ADD)
	public String add(Model model) {
		model.addAttribute("obj", Result.success().setTitle("添加用户"));
		return "pages/admin/auth/user/add_edit";
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 *            待添加用户
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ThunderRequiresPermissions(InstallPermission.USER_ADD)
	public @ResponseBody Result add(User user) {
		user.setPassword(Lang.md5(user.getPassword()));
		return userService.save(user) != null ? Result.success().addData("user", user) : Result.fail("添加用户失败!");
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 *            用户id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ThunderRequiresPermissions(InstallPermission.USER_DELETE)
	public @ResponseBody Result delete(@PathVariable("id") int id) {
		return userService.delete(id) == 1 ? Result.success() : Result.fail("删除用户失败!");
	}

	/**
	 * 用户详情
	 * 
	 * @param id
	 *            用户id
	 * @return
	 */
	@RequestMapping("/detail/{id}")
	@ThunderRequiresPermissions(InstallPermission.USER_DETAIL)
	public String detail(@PathVariable("id") int id, Model model) {
		model.addAttribute("obj", Result.success().addData("user", userService.fetch(id)).setTitle("用户详情"));
		return "pages/admin/auth/user/detail";
	}

	/**
	 * 编辑用户页面
	 * 
	 * @param id
	 *            用户id
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	@Ok("beetl:pages/admin/auth/user/add_edit.html")
	@ThunderRequiresPermissions(InstallPermission.USER_EDIT)
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("obj", Result.success().addData("user", userService.fetch(id)).setTitle("编辑用户"));
		return "pages/admin/auth/user/add_edit";
	}

	/**
	 * 编辑用户
	 * 
	 * @param user
	 *            待更新用户
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ThunderRequiresPermissions(InstallPermission.USER_EDIT)
	public @ResponseBody Result edit(User user) {
		return userService.update(user, "realName", "phone", "email", "status") ? Result.success() : Result.fail("更新失败!");
	}

	/**
	 * 授权
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/grant/{id}", method = RequestMethod.GET)
	@ThunderRequiresPermissions(InstallPermission.USER_GRANT)
	public String grant(@PathVariable("id") int id, Model model) {
		List<Record> records = userService.findPermissionsWithUserPowerdInfoByUserId(id);
		model.addAttribute("obj", Result.success().addData("records", records).addData("userId", id).setTitle("用户授权"));
		return "pages/admin/auth/user/grant";
	}

	/**
	 * 授权
	 * 
	 * @param ids
	 * @param id
	 * @return
	 */
	@RequestMapping("grant")
	@ThunderRequiresPermissions(InstallPermission.USER_GRANT)
	public @ResponseBody Result grant(@RequestParam(value = "permissions", defaultValue = "") int[] ids, @RequestParam("id") int id) {
		return userService.setPermission(ids, id);
	}

	/**
	 * 用户列表
	 * 
	 * @param page
	 *            页码
	 * @return
	 */
	@RequestMapping("list")
	@ThunderRequiresPermissions(InstallPermission.USER_LIST)
	@SystemLog(module = "用户管理", methods = "用户列表")
	public String list(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		page = _fixPage(page);
		Pager<User> pager = userService.searchByPage(page);
		pager.setUrl(_base() + "/user/list");
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle("用户列表"));
		return "pages/admin/auth/user/list";
	}

	/**
	 * 设置角色
	 *
	 * @param id
	 *            用户id
	 * @return
	 */
	@RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
	@ThunderRequiresPermissions(InstallPermission.USER_ROLE)
	public String role(@PathVariable("id") int id, Model model) {
		List<Record> records = userService.findRolesWithUserPowerdInfoByUserId(id);
		model.addAttribute("obj", Result.success().addData("records", records).addData("userId", id).setTitle("用户角色"));
		return "pages/admin/auth/user/role";
	}

	/**
	 * 设置角色
	 *
	 * @param ids
	 *            角色ID数组
	 * @param id
	 *            用户id
	 * @return
	 */
	@RequestMapping("role")
	@ThunderRequiresPermissions(InstallPermission.USER_ROLE)
	public @ResponseBody Result role(@RequestParam(value = "roles", defaultValue = "") int[] ids, @RequestParam("id") int id) {
		return userService.setRole(ids, id);
	}

	/**
	 * 搜索用户
	 * 
	 * @param key
	 *            关键词
	 * @param page
	 *            页码
	 * @return
	 */
	@RequestMapping("search")
	@ThunderRequiresPermissions(InstallPermission.USER_LIST)
	public String search(@RequestParam("key") String key, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		page = _fixPage(page);
		key = _fixSearchKey(key);
		Pager<User> pager = userService.searchByKeyAndPage(key, page, "name", "nickName", "realName");
		pager.setUrl(_base() + "/user/search");
		pager.addParas("key", key);
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle("用户检索"));
		return "pages/admin/auth/user/list";
	}

}
