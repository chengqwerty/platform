package som.make.botany.cryptography.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import som.make.botany.cryptography.config.condition.LinuxCondition;
import som.make.botany.cryptography.config.condition.WindowsCondition;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    /**
//     * windows condition 禁用 spring security
//     * @return WebSecurityConfigurerAdapter
//     */
//    @Bean
//    @Conditional(WindowsCondition.class)
//    public WebSecurityConfigurerAdapter WindowsWebSecurityConfigurerAdapter() {
//        return new WebSecurityConfigurerAdapter() {
//            @Override
//            public void configure(WebSecurity web) throws Exception {
//                super.configure(web);
//                web.ignoring().antMatchers("/**");
//            }
//        };
//    }


    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                super.configure(http);
                http.formLogin().successHandler(new CustomAuthenticationSuccessHandler()).failureHandler(new CustomAuthenticationFailureHandler())
                        .and()
                        .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .and()
                        .logout().logoutSuccessHandler(new CustomLogoutSuccessHandler())
                        .and()
                        .csrf().disable(); // 暂时禁用，后面研究清楚了再启用
            }
        };
    }

}
