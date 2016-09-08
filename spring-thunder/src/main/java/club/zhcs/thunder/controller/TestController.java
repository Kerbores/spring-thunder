package club.zhcs.thunder.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project spring-thunder
 *
 * @file TestController.java
 *
 * @description // TODO write some description of this class
 *
 * @time 2016年9月7日 下午11:50:43
 *
 */
@Controller
@RequestMapping("test")
public class TestController {

	@RequestMapping("/json")
	public @ResponseBody Map<String, Object> json() {

		HashMap<String, Object> target = new HashMap<String, Object>();

		target.put("msg", "Hello spring-mvc");

		return target;
	}

	@RequestMapping("/beetl")
	public String beetl(Model model) {
		model.addAttribute("obj", "Hello iBeetl!");
		return "pages/test";
	}
}
