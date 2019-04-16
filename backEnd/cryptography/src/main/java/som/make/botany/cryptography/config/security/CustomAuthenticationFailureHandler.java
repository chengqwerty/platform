package som.make.botany.cryptography.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import som.make.botany.cryptography.common.beans.ResultBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 使用前后端分离登录失败处理。
 * 前端获取登录失败的信息进行处理。
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        ResultBean<String> resultBean = new ResultBean<>();
        resultBean.setCode(ResultBean.CHECK_FAIL);
        response.getWriter().write(objectMapper.writeValueAsString(resultBean));
    }

}
