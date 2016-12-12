package club.zhcs.thunder.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import club.zhcs.thunder.Thunder.SessionKeys;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.thunder.domain.acl.User;
import club.zhcs.thunder.mapper.UserMapper;
import club.zhcs.thunder.tasks.APMTask;
import club.zhcs.titans.nutz.captcha.ImageVerification;
import club.zhcs.titans.nutz.captcha.JPEGView;
import club.zhcs.titans.utils.codec.DES;
import club.zhcs.titans.utils.db.Result;

/**
 * @author admin
 *
 * @email kerbores@gmail.com
 *
 */
@Controller
public class HomeController extends BaseController {

	@Autowired
	APMTask apmTask;

	@RequestMapping("/")
	public String home(Model model, @SessionAttribute(name = SessionKeys.USER_KEY, required = false) User user) {
		if (user != null) {
			return "redirect:/system/main";
		}
		String cookie = _getCookie("kerbores");
		NutMap data = NutMap.NEW();
		if (!Strings.isBlank(cookie)) {
			data = Lang.map(DES.decrypt(cookie));
		}
		model.addAttribute("loginInfo", data);
		return "pages/login/login";
	}

	@Autowired
	UserMapper userMapper;

	@RequestMapping("/mapper")
	public @ResponseBody Page<club.zhcs.thunder.bean.User> mapper() {
		Page<club.zhcs.thunder.bean.User> page = PageHelper.startPage(1, 10);
		userMapper.selectAll();
		System.err.println(page.getPageNum());
		return page;
	}

	@RequestMapping("/captcha")
	public void captcha(@RequestParam("length") int length, HttpServletResponse resp, HttpSession session)
			throws IOException {
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
			logger.debug("写入输出流成功:" + iv.getVerifyCode() + ".");
		} else {
			logger.debug("写入输出流失败:" + iv.getVerifyCode() + ".");
		}

		session.setAttribute(JPEGView.CAPTCHA, iv.getVerifyCode());

		// 以下关闭输入流！
		out.flush();
		out.close();
	}

	@RequestMapping("dashboard")
	public @ResponseBody Result dashboard() {
		return apmTask.data();
	}

}
