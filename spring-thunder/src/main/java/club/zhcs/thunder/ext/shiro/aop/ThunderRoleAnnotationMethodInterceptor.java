package club.zhcs.thunder.ext.shiro.aop;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.aop.RoleAnnotationMethodInterceptor;

/**
 * 
 * @author kerbores
 *
 * @email kerbores@gmail.com
 *
 */
public class ThunderRoleAnnotationMethodInterceptor extends RoleAnnotationMethodInterceptor {

	public ThunderRoleAnnotationMethodInterceptor() {
		setHandler(new ThunderRoleAnnotationHandler());
	}

	public ThunderRoleAnnotationMethodInterceptor(AnnotationResolver resolver) {
		setHandler(new ThunderRoleAnnotationHandler());
		setResolver(resolver);
	}
}
