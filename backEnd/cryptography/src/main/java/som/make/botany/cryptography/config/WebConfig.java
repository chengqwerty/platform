package som.make.botany.cryptography.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import som.make.botany.cryptography.config.condition.WindowsCondition;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * windows condition 允许跨域，正式环境不允许跨域
     * @return WebMvcConfigurer
     */
    @Bean
    @Conditional(WindowsCondition.class)
    public WebMvcConfigurer windowsWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    /**
     * windows condition 自定义 filter 解决 withCredentials: true 带来的浏览器更加严格的跨域问题
     * @return FilterRegistrationBean
     */
    @Bean
    @Conditional(WindowsCondition.class)
    public FilterRegistrationBean registrationHeadFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new HeadFilter());
        filterRegistrationBean.setOrder(-101);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
