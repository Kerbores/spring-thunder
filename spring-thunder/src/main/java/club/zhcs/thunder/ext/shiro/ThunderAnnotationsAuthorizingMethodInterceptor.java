package club.zhcs.thunder.ext.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.aop.AuthenticatedAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.GuestAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.PermissionAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.RoleAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.UserAnnotationMethodInterceptor;
import org.apache.shiro.spring.aop.SpringAnnotationResolver;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;

import club.zhcs.thunder.ext.shiro.aop.ThunderPermissionAnnotationMethodInterceptor;
import club.zhcs.thunder.ext.shiro.aop.ThunderRoleAnnotationMethodInterceptor;

/**
 * @author admin
 *
 * @email kerbores@gmail.com
 *
 */
public class ThunderAnnotationsAuthorizingMethodInterceptor extends AopAllianceAnnotationsAuthorizingMethodInterceptor {

	public ThunderAnnotationsAuthorizingMethodInterceptor() {
		List interceptors = new ArrayList(7);

		AnnotationResolver resolver = new SpringAnnotationResolver();

		interceptors.add(new RoleAnnotationMethodInterceptor(resolver));
		interceptors.add(new PermissionAnnotationMethodInterceptor(resolver));
		interceptors.add(new AuthenticatedAnnotationMethodInterceptor(resolver));
		interceptors.add(new UserAnnotationMethodInterceptor(resolver));
		interceptors.add(new GuestAnnotationMethodInterceptor(resolver));
		interceptors.add(new ThunderRoleAnnotationMethodInterceptor(resolver));
		interceptors.add(new ThunderPermissionAnnotationMethodInterceptor(resolver));

		setMethodInterceptors(interceptors);
	}
}
