package club.zhcs.thunder.ext.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author admin
 *
 * @email kerbores@gmail.com
 *
 */
public class SpringBeans {

	public static ApplicationContext applicationContext = null;

	public static HttpServletRequest getRequest() {
		ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return ra.getRequest();
	}

	public static HttpServletResponse getrResponse() {
		ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return ra.getResponse();
	}

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	public static <T> T getBean(String id, Class<T> clazz) {
		return applicationContext.getBean(id, clazz);
	}
}
