package club.zhcs.thunder.controller.admin.log;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.nutz.mvc.annotation.Ok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import club.zhcs.thunder.biz.log.OperationLogService;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.thunder.domain.log.OperationLog;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

/**
 * 
 * @author admin
 *
 * @email kerbores@gmail.com
 *
 */
@Controller
@RequestMapping("operation")
public class OperationLogController extends BaseController {

	@Autowired
	OperationLogService operationLogService;

	@RequestMapping("list")
	@RequiresRoles("admin")
	public String list(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		Pager<OperationLog> pager = operationLogService.searchByPage(_fixPage(page));
		pager.setUrl(_base() + "/operation/list");
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle("操作日志列表"));
		return "pages/log/operation/list";
	}

	@RequestMapping("search")
	@Ok("beetl:pages/log/operation/list.html")
	@RequiresRoles("admin")
	public String search(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam("key") String key, Model model) {
		Pager<OperationLog> pager = operationLogService.searchByKeyAndPage(_fixSearchKey(key), _fixPage(page), "account", "ip", "module", "action", "description");
		pager.setUrl(_base() + "/operation/list");
		pager.addParas("key", key);
		model.addAttribute("obj", Result.success().addData("pager", pager).setTitle("操作日志列表"));
		return "pages/log/operation/list";
	}

}
