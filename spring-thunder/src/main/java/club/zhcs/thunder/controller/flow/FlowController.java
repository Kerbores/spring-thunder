package club.zhcs.thunder.controller.flow;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import club.zhcs.thunder.controller.base.BaseController;

/**
 * @author admin
 *
 * @email kerbores@gmail.com
 *
 */
@Controller
@RequestMapping("flow")
public class FlowController extends BaseController {

	@Autowired
	RepositoryService repositoryService;

	@RequestMapping("list")
	public @ResponseBody List<Model> list() {
		return repositoryService.createModelQuery().list();
	}
}
