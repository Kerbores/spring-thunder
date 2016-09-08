package club.zhcs.thunder.controller;

import java.util.HashMap;
import java.util.Map;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @description 测试
 *
 * @time 2016年9月7日 下午11:50:43
 *
 */
@Controller
@RequestMapping("test")
public class TestController {

	@Autowired
	Dao dao;

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

	@RequestMapping("/db")
	public @ResponseBody NutMap db() {
		return NutMap.NEW().addv("db", dao.meta());
	}

	@RequestMapping("/sql")
	public @ResponseBody int sql() {
		Sql sql = dao.sqls().create("test");
		sql.setCallback(Sqls.callback.integer());
		dao.execute(sql);
		return sql.getInt();
	}
}
