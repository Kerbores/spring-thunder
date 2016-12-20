package club.zhcs.thunder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.titans.utils.db.Result;

/**
 * @author admin
 *
 * @email kerbores@gmail.com
 *
 */
@Controller
@RequestMapping("k")
public class K extends BaseController {

	@RequestMapping("d")
	@ResponseBody
	public Result name() {
		return Result.success();
	}

	@RequestMapping("m")
	public String message() {
		return "pages/message/message";
	}
}
