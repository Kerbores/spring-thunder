package club.zhcs.thunder.biz.acl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import club.zhcs.thunder.biz.log.LoginLogService;
import club.zhcs.thunder.domain.acl.Permission;
import club.zhcs.thunder.domain.acl.Role;
import club.zhcs.thunder.domain.acl.User;
import club.zhcs.thunder.domain.acl.User.Type;
import club.zhcs.thunder.domain.log.LoginLog;
import club.zhcs.titans.utils.db.Result;

/**
 * 
 * @author kerbores
 *
 * @email kerbores@gmail.com
 *
 */
@Service
public class ShiroUserService {
	Log log = Logs.get();
	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	PermissionService permissionService;

	@Autowired
	LoginLogService loginLogService;

	/**
	 * 检查权限
	 * 
	 * @param permission
	 *            权限名称
	 * @param id
	 *            用户 id
	 * @return 用户是否有参数权限的标识
	 *
	 * @author 王贵源
	 */
	public boolean checkPermission(String permission, int id) {

		for (String p : getAllPermissionsInfo(id)) {
			if (Strings.equals(p, permission)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查角色
	 * 
	 * @param role
	 *            角色名称
	 * @param id
	 *            用户 id
	 * @return 用户是否有参数角色的标识
	 *
	 * @author 王贵源
	 */
	public boolean checkRole(String role, int id) {
		for (String r : getRolesInfo(id)) {
			if (Strings.equals(role, r)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据用户名查询用户
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户
	 *
	 * @author 王贵源
	 */
	public User findByName(String userName) {
		return userService.fetch(userName);
	}

	/**
	 * 查询用户的全部权限
	 * 
	 * @param id
	 *            用户 id
	 * @return 权限列表
	 *
	 * @author 王贵源
	 */
	public List<Permission> getAllPermissions(int id) {
		return permissionService.getAllPermissionsByUserId(id);
	}

	/**
	 * 根据用户获取权限信息
	 * 
	 * @param id
	 *            用户 id
	 * @return 权限名称列表
	 *
	 * @author 王贵源
	 */
	public List<String> getAllPermissionsInfo(int id) {
		List<Permission> permissions = getAllPermissions(id);
		final List<String> target = Lists.newArrayList();
		Lang.each(permissions, new Each<Permission>() {

			@Override
			public void invoke(int index, Permission ele, int length) throws ExitLoop, ContinueLoop, LoopException {
				target.add(ele.getName());
			}
		});
		return target;
	}

	/**
	 * 获取用户全部角色
	 * 
	 * @param id
	 *            用户 id
	 * @return 角色列表
	 *
	 * @author 王贵源
	 */
	public List<Role> getAllRoles(int id) {
		// XXX 直接权限即全部权限
		return getDirectRoles(id);
	}

	/**
	 * 获取用户直接角色
	 * 
	 * @param id
	 *            用户 id
	 * @return 角色列表
	 *
	 * @author 王贵源
	 */
	public List<Role> getDirectRoles(int id) {
		return roleService.listByUserId(id);
	}

	/**
	 * 获取用户间接角色
	 * 
	 * @param id
	 *            用户 id
	 * @param type
	 *            用户类型
	 * @return 角色列表
	 *
	 * @author 王贵源
	 */
	public List<Role> getIndirectRoles(int id, Type type) {
		return Lists.newArrayList();
	}

	/**
	 * 获取用户的菜单权限
	 * 
	 * @param id
	 *            用户 id
	 * @return 权限列表
	 *
	 * @author 王贵源
	 */
	public List<Permission> getMenuPermissions(int id) {
		List<Permission> permissions = getAllPermissions(id);
		final List<Permission> target = Lists.newArrayList();
		Lang.each(permissions, new Each<Permission>() {

			@Override
			public void invoke(int index, Permission ele, int length) throws ExitLoop, ContinueLoop, LoopException {
				if (ele.isMenu()) {
					target.add(ele);
				}
			}
		});
		return target;
	}

	/**
	 * 获取用户的角色信息列表
	 * 
	 * @param id
	 *            用户 id
	 * @return 角色名称列表
	 *
	 * @author 王贵源
	 */
	public List<String> getRolesInfo(int id) {
		final List<String> roles = Lists.newArrayList();
		Lang.each(getAllRoles(id), new Each<Role>() {

			@Override
			public void invoke(int index, Role ele, int length) throws ExitLoop, ContinueLoop, LoopException {
				roles.add(ele.getName());
			}
		});
		return roles;
	}

	/**
	 * 用户登录
	 *
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return 登录结果
	 *
	 * @author 王贵源
	 */
	public Result login(String userName, String password) {
		try {
			User user = findByName(userName);
			if (user == null) {
				return Result.fail("用户名或密码不存在");
			}
			if (!user.isAvailable()) {
				return Result.fail("账户被锁定");
			}
			Subject currentUser = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
			token.setRememberMe(true);
			currentUser.login(token);
			LoginLog log = new LoginLog();
			log.setAccount(userName);
			log.setUserId(user.getId());
			log.setIp(Lang.getIP(Mvcs.getReq()));
			loginLogService.save(log);
			return Result.success().addData("loginUser", user);
		} catch (LockedAccountException e) {
			log.debug(e);
			return Result.fail("账户被锁定");
		} catch (Exception e) {
			log.debug(e);
			return Result.fail("登录失败");
		}
	}

}
