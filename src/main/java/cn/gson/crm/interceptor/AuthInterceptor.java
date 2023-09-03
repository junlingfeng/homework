package cn.gson.crm.interceptor;

import cn.gson.crm.common.ApiResult;
import cn.gson.crm.common.ApiResultEnum;
import cn.gson.crm.controller.system.RoleController;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 权限URL拦截器
 *
 * @author gson
 */
public class AuthInterceptor implements HandlerInterceptor {

    public static final String TOKEN = "TOKEN";
    public static final int TWO_HOUR = 2 * 3600 * 1000;

    @Override
    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        HandlerMethod hm = (HandlerMethod) handler;
        if (hm.getBean() instanceof RoleController) {
            //App里面的就不做权限控制了，相当于公共的，省的写一个就要配置一个例外
            //超管也直接跳过权限
            return true;
        }

        String serverToken = (String) session.getAttribute(TOKEN);
        String token = request.getHeader(TOKEN);
        if (!StringUtils.equals(serverToken, token)) {
            response.setStatus(401);
            response.getWriter().println(ApiResultEnum.INVALID_ACCESS.getMessage());
            return false;
        }
        long createTime = Long.parseLong(token.split("@")[0]);
        if (System.currentTimeMillis() - createTime > TWO_HOUR) {
            session.removeAttribute(TOKEN);
            session.invalidate();
            response.setStatus(401);
            response.getWriter().println(ApiResultEnum.INVALID_ACCESS.getMessage());
            return false;
        }

        return true;
    }

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {

    }

}
