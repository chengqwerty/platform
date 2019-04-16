package som.make.botany.cryptography.config;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理跨域问题
 */
public class HeadFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        String origin = httpServletRequest.getHeader("Origin");
        System.out.println(httpServletResponse.getHeader("Set-Cookie"));
        if(!StringUtils.isEmpty(origin)){ // 如果域名不为空，则获取请求的域名
            System.out.println(origin);
            httpServletResponse.setHeader("Access-Control-Allow-Origin", origin); // 允许访问的域名
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
            httpServletResponse.setHeader("Access-Control-Allow-Headers",
                    "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        }
        System.out.println(httpServletResponse.getHeader("Access-Control-Allow-Origin"));
        System.out.println(httpServletResponse.getHeader("Access-Control-Allow-Credentials"));
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
