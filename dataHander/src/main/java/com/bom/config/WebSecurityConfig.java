/*
package com.bom.config;

import com.bom.utils.security.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import  com.bom.utils.security.CustomUserService;
*/
/**
 * Created by jindb on 2017/7/24.
 *//*

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService customUserService;
    */
/**
     * 登录session key
     *//*

    public final static String SESSION_KEY = "user";


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();*/
/*Spring Boot不允许加载iframe问题解决*//*

        http
                .authorizeRequests()
                .antMatchers("/login","/lib*/
/**","/js*/
/**","/css*/
/**","/image*/
/**","/images*/
/**","/fonts*/
/**","*/
/**//*
favicon.ico","/siteinfo/GetSiteCount","/siteinfo/GetSiteList","/analysis/indexvip").permitAll()//访问：/home 无需登录认证权限
                .anyRequest()
                .authenticated() //其他所有资源都需要认证，登陆后访问
                .antMatchers("/analysis/home/index").hasAuthority("ADMIN") //登陆后之后拥有“ADMIN”权限才可以访问/hello方法，否则系统会出现“403”权限不足的提示
                .and()
                .formLogin()
                .loginPage("/login")//指定登录页是”/login”
                .loginProcessingUrl("/login/post")
                .failureUrl("/login?error=true")
                .permitAll()
                .successHandler(loginSuccessHandler()) //登录成功后可使用loginSuccessHandler()存储用户信息，可选。
                .and()
                .logout()
                .logoutSuccessUrl("/login") //退出登录后的默认网址是”/home”
                .permitAll()
                .invalidateHttpSession(true)
                .and().csrf().disable();
               // .rememberMe()//登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
               // .tokenValiditySeconds(1209600);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService);
        auth.eraseCredentials(false);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }

}
*/
