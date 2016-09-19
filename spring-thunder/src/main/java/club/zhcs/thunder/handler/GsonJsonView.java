package club.zhcs.thunder.handler;

import com.google.gson.Gson;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by admin on 2016/9/19.
 */
public class GsonJsonView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(map));
    }
}
