package cn.com.scitc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class WebSecurityConfg extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .authorizeRequests()
                .antMatchers("/500").permitAll()
                .antMatchers("/404").permitAll()
                .antMatchers("/403").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/admin/index").hasRole("ADMIN")//指定权限为ADMIN才能访问
                .antMatchers("/person").hasAnyRole("ADMIN","USER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()//使用表单认证方式
                .loginProcessingUrl("/authentication/form")//配置默认登录入口
                .successHandler(myLoginSuccessHandler)
                .failureHandler(myLoginFailureHandler)
                .and()
                .csrf().disable();
    }

    /**
     * 自定义认证策略
     *
     * @return
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        String password = passwordEncoder().encode("123456");

        logger.info("加密后的密码:" + password);

//        auth.inMemoryAuthentication().withUser("admin").password(password)
//                .roles("ADMIN").and();

        auth.inMemoryAuthentication().withUser("user").password(password)
                .roles("USER").and();

    }

    @Autowired
    private AuthenticationSuccessHandler myLoginSuccessHandler; //认证成功结果处理器

    @Autowired
    private AuthenticationFailureHandler myLoginFailureHandler; //认证失败结果处理器

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
