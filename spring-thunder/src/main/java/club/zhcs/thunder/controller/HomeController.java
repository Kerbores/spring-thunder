package club.zhcs.thunder.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import club.zhcs.thunder.Thunder.SessionKeys;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.titans.nutz.captcha.ImageVerification;
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

	public static final String CAPTCHA = "KERBORES_NUTZ_CAPTCHA";
	private static final Logger log = Logger.getLogger(HomeController.class);

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

	@RequestMapping("/captcha")
	public void captcha(@RequestParam("length") int length, HttpServletResponse resp, HttpSession session) throws IOException {
		resp.setContentType("image/jpeg");
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);

		OutputStream out = resp.getOutputStream();
		// 输出图象到页面
		ImageVerification iv = new ImageVerification();

		if (length != 0) {
			iv.setIMAGE_VERIFICATION_LENGTH(length);
		}

		if (ImageIO.write(iv.creatImage(), "JPEG", out)) {
			log.debug("写入输出流成功:" + iv.getVerifyCode() + ".");
		} else {
			log.debug("写入输出流失败:" + iv.getVerifyCode() + ".");
		}

		session.setAttribute(CAPTCHA, iv.getVerifyCode());

		// 以下关闭输入流！
		out.flush();
		out.close();
	}

}
