package som.make.botany.cryptography.config.security.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import som.make.botany.cryptography.system.bean.SysUserBean;
import som.make.botany.cryptography.system.dao.SysUserDao;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter
//@Order(2)
//@Component
//@Lazy()
public class LoginFilter implements Filter {

    @Autowired
    private SysUserDao sysUserDao;

    private String matchPath = "/login";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (matches(request)) {
            SysUserBean sysUserBean = sysUserDao.findByLoginName(request.getParameter("username"));
            System.out.println(sysUserBean);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    private String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();

        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            url = StringUtils.hasLength(url) ? url + pathInfo : pathInfo;
        }

        return url;
    }

    public boolean matches(HttpServletRequest request) {
        String url = getRequestPath(request);
        return url.startsWith(this.matchPath)
                && (url.length() == this.matchPath.length() || matchPath.charAt(this.matchPath.length()) == '/');
    }
}
