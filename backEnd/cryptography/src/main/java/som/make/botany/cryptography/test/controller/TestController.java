package som.make.botany.cryptography.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import som.make.botany.cryptography.test.config.AuthorConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private AuthorConfig authorConfig;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("testAuthor")
    public AuthorConfig testAuthor() {
        return authorConfig;
    }

    @RequestMapping()
    public String test() {
        return "This is test!";
    }

    @RequestMapping("setSession")
    public String setSession(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().setAttribute("sessionId", "chengcheng");
        return "Set session success!";
    }

    @RequestMapping("getSession")
    public String getSession(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        String value = (String) httpSession.getAttribute("chengcheng");
        if (StringUtils.isEmpty(value)) {
            httpSession.setAttribute("chengcheng", ((Double)Math.random()).toString());
            value = (String) httpSession.getAttribute("chengcheng");
        }
        System.out.println(value);
        System.out.println(httpSession.getCreationTime());
        System.out.println(httpSession.getLastAccessedTime());
        System.out.println(httpSession.getMaxInactiveInterval());
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateTimeFormatter.format(localDateTime));
        return "This is session!";
    }

    @RequestMapping("getSession2")
    public String getSession2() {
        String sessionId = (String) httpServletRequest.getSession().getAttribute("sessionId");
        return "sessionId is " + sessionId;
    }

}
