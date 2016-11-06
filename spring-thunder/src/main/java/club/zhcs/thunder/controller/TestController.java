package club.zhcs.thunder.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import club.zhcs.thunder.aop.SystemLog;
import club.zhcs.thunder.biz.acl.UserService;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.thunder.domain.InstalledRole;
import club.zhcs.thunder.domain.acl.User;
import club.zhcs.thunder.ext.shiro.anno.ThunderRequiresRoles;

/**
 * @author Kerbores(kerbores@gmail.com)
 * @project spring-thunder
 * @file TestController.java
 * @description 测试
 * @time 2016年9月7日 下午11:50:43
 */
@Controller
@RequestMapping("/test/*")
public class TestController extends BaseController {

	@Autowired
	Dao dao;

	@Autowired
	UserService userService;

	@RequestMapping("/users")
	public @ResponseBody List<User> users() {
		return userService.queryAll();
	}

	@RequestMapping("/json")
	public @ResponseBody Map<String, Object> json() {

		HashMap<String, Object> target = new HashMap<String, Object>();

		target.put("msg", "Hello spring-thunder");

		return target;
	}

	@RequestMapping("/list")
	public @ResponseBody List<Map<String, Object>> list() {
		List<Map<String, Object>> target = Lists.newArrayList();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> temp = NutMap.NEW();
			temp.put("lng", R.random(1000, 10000) / 100.00d);
			temp.put("lat", R.random(1000, 10000) / 100.00d);
			temp.put("count", R.random(10, 100));
			target.add(temp);
		}

		return target;
	}

	@RequestMapping("/beetl")
	public String beetl(Model model) {
		model.addAttribute("obj", "Hello iBeetl!");
		return "pages/test";
	}

	@RequestMapping("/db")
	@SystemLog(module = "测试", methods = "db")
	public @ResponseBody NutMap db() {
		return NutMap.NEW().addv("db", dao.meta());
	}

	@RequestMapping("/shiro")
	@ThunderRequiresRoles(InstalledRole.SU)
	public @ResponseBody NutMap shiro() {
		return NutMap.NEW().addv("msg", "shiro");
	}

	@RequestMapping("/sql")
	public @ResponseBody List<Record> sql() {
		Sql sql = dao.sqls().create("test");
		sql.vars().set("id", 1);
		sql.setCallback(Sqls.callback.records());
		dao.execute(sql);
		return sql.getList(Record.class);
	}
}
