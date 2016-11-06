package club.zhcs.thunder.controller;

import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import club.zhcs.thunder.aop.SystemLog;
import club.zhcs.titans.utils.db.Result;

/**
 * @author kerbores
 *
 * @email kerbores@gmail.com
 *
 */
@Controller
@RequestMapping("test")
public class T {

	@Autowired
	Dao dao;

	@RequestMapping("h")
	@ResponseBody
	public Result h() {
		return Result.success();
	}

	@RequestMapping("db")
	@SystemLog(module = "测试", methods = "db")
	public @ResponseBody Result db() {
		return Result.success().addData("db", dao.meta());
	}
}
