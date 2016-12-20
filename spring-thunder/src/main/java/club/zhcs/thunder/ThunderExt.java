package club.zhcs.thunder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.web.WebRenderExt;

public class ThunderExt implements WebRenderExt {

	@Override
	public void modify(Template template, GroupTemplate groupTemplate, HttpServletRequest request, HttpServletResponse response) {
		template.binding("response", response);
	}

}
