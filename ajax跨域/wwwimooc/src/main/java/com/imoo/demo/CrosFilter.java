package com.imoo.demo;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CrosFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res=(HttpServletResponse) servletResponse;

        //解决多个域名的处理方式，从request中取出Origin 在重新设置
        HttpServletRequest req= (HttpServletRequest) servletRequest;
        String origin = req.getHeader("Origin");
        if(!org.springframework.util.StringUtils.isEmpty(origin))
        {
            //带cookie的时候必须是全匹配，不能使用*，需要启用Access-Control-Allow-Credentials
            res.addHeader("Access-Control-Allow-Origin",origin);
        }

//        //带cookie的时候必须是全匹配，不能使用*，需要启用Access-Control-Allow-Credentials
//        res.addHeader("Access-Control-Allow-Origin","http://localhost:8081");
        res.addHeader("Access-Control-Allow-Methods","*");

        //支持所有自定头
        String header = req.getHeader("Access-Control-Request-Headers");
        if(!org.springframework.util.StringUtils.isEmpty(header)){
            res.addHeader("Access-Control-Allow-Headers",header);//option 预检命令
        }
//        res.addHeader("Access-Control-Allow-Headers","Content-Type,x-header1,x-header2");//option 预检命令
        res.addHeader("Access-Control-Max-Age","3600"); //设置预检命令缓存
        //启用cookie
        res.addHeader("Access-Control-Allow-Credentials","true");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
