package club.zhcs.thunder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by admin on 2016/9/19.
 */
@Controller
public class ErrorController {

	@RequestMapping("/404")
	public String _404() {
		return "pages/error/404";
	}
	
	@RequestMapping("/500")
	public String _500() {
		return "pages/error/500";
	}
}
