package club.zhcs.thunder.controller.base;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.mvc.View;
import org.nutz.mvc.view.ForwardView;
import org.nutz.mvc.view.JspView;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.mvc.view.UTF8JsonView;

import club.zhcs.thunder.ext.spring.SpringBeans;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * @author admin
 *
 * @email kerbores@gmail.com
 *
 */
public class BaseController {

	public Logger logger = Logger.getLogger(getClass());

	protected void _addCookie(String name, String value, int age) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(age);
		SpringBeans.getrResponse().addCookie(cookie);
	}

	public String _base() {
		return SpringBeans.getRequest().getContextPath();
	}

	public UserAgent _ua() {
		return new UserAgent(SpringBeans.getRequest().getHeader("user-agent"));
	}

	public int _fixPage(int page) {
		return ((page <= 0) ? 1 : page);
	}

	public String _fixSearchKey(String key) {
		HttpServletRequest request = SpringBeans.getRequest();
		if ((Strings.equalsIgnoreCase("get", request.getMethod())) && (Lang.isWin())) {
			key = (Strings.isBlank(key)) ? "" : key;
			try {
				return new String(key.getBytes("iso-8859-1"), request.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				logger.debug(e);
				return "";
			}
		}
		return ((Strings.isBlank(key)) ? "" : key);
	}

	protected String _getCookie(String name) {
		Cookie[] cookies = SpringBeans.getRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (Strings.equals(cookie.getName(), name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public String _getNameSpace() {
		return null;
	}

	public String _ip() {
		return Lang.getIP(SpringBeans.getRequest());
	}

	protected void _putSession(String key, Object value) {
		SpringBeans.getRequest().getSession().setAttribute(key, value);
	}

	public View _renderForward(String path, Object[] objs) {
		SpringBeans.getRequest().setAttribute("objs", objs);
		return new ForwardView(path);
	}

	public View _renderJson(Object[] objs) {
		UTF8JsonView view = (UTF8JsonView) UTF8JsonView.NICE;
		view.setData(objs);
		return view;
	}

	public View _renderJsp(String path, Object[] objs) {
		SpringBeans.getRequest().setAttribute("objs", objs);
		return new JspView(path);
	}

	public View _renderRedirct(String path) {
		return new ServerRedirectView(path);
	}
}
