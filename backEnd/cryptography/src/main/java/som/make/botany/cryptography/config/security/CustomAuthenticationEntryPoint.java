package som.make.botany.cryptography.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 使用前后端分离所以不使用默认的跳转登录页面，对于未登录的自定义处理返回401错误。
 * 前端捕获401后，自动跳转登录页面
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (request.getMethod().equals("OPTIONS")) {
            response.sendError(HttpServletResponse.SC_OK, authException.getMessage());
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        }
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
//        if(isAjaxRequest(request)){
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
//        }else{
//            response.sendRedirect("/login");
//        }
    }

    private static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }

}
