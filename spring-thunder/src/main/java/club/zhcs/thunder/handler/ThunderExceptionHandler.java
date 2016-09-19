package club.zhcs.thunder.handler;

import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2016/9/19.
 */
public class ThunderExceptionHandler  implements HandlerExceptionResolver{
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        String requestType = request.getHeader("X-Requested-With");
        if (Strings.isBlank(requestType))
        return null;

        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setAttributesMap(NutMap.NEW().addv("error",e.getMessage()));
        mav.setView(view);
        return mav;
    }
}
