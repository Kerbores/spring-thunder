package club.zhcs.thunder.ext.shiro;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.nutz.lang.Lang;
import org.nutz.mvc.Mvcs;

import club.zhcs.thunder.biz.acl.ShiroUserService;
import club.zhcs.thunder.domain.acl.User;
import club.zhcs.thunder.domain.acl.User.Status;
import club.zhcs.thunder.ext.spring.SpringBeans;

/**
 * 
 * @author kerbores
 *
 * @email kerbores@gmail.com
 *
 */
public class ThunderRealm extends AuthorizingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		HttpSession session = Mvcs.getReq().getSession();
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String userName = upToken.getUsername();
		User user = getUserService().findByName(userName);
		if (Lang.isEmpty(user))// 用户不存在
			return null;
		if (user.getStatus() == Status.DISABLED)// 用户被锁定
			throw new LockedAccountException("Account [" + upToken.getUsername() + "] is locked.");

		SimpleAuthenticationInfo account = new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());

		List<String> roleNameList = getUserService().getRolesInfo(user.getId());
		session.setAttribute("roles", roleNameList);
		List<String> permissionNames = getUserService().getAllPermissionsInfo(user.getId());
		session.setAttribute("permissions", permissionNames);
		return account;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		HttpSession session = Mvcs.getReq().getSession();
		String userName = principalCollection.getPrimaryPrincipal().toString();
		User user = getUserService().findByName(userName);
		if (user == null)// 用户不存在
			return null;
		if (user.getStatus() == Status.DISABLED)// 用户被锁定
			throw new LockedAccountException("Account [" + userName + "] is locked.");
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
		List<String> roleNameList = getUserService().getRolesInfo(user.getId());
		session.setAttribute("roles", roleNameList);
		auth.addRoles(roleNameList);// 添加角色
		List<String> permissionNames = getUserService().getAllPermissionsInfo(user.getId());
		session.setAttribute("permissions", permissionNames);
		auth.addStringPermissions(permissionNames);// 添加权限
		return auth;
	}

	private ShiroUserService getUserService() {
		return SpringBeans.getBean(ShiroUserService.class);
	}
}
