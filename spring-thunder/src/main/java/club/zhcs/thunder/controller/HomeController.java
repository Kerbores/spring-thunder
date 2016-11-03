package club.zhcs.thunder.controller;

import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import club.zhcs.thunder.Thunder.SessionKeys;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.titans.utils.codec.DES;

/**
 * @author admin
 *
 * @email kerbores@gmail.com
 *
 */
@Controller
@RequestMapping("/*")
public class HomeController extends BaseController {

	@RequestMapping("/")
	public String home(HttpServletRequest request, Model model) {
		Object user = request.getSession().getAttribute(SessionKeys.USER_KEY);
		if (user != null) {
			return "redirect:/system/main";
		}
		String cookie = _getCookie("kerbores");
		NutMap data = NutMap.NEW();
		if (!Strings.isBlank(cookie)) {
			data = Lang.map(DES.decrypt(cookie));
		}
		request.setAttribute("loginInfo", data);
		return "pages/login/login";
	}

}
