/*
package com.bom.utils.security;


import com.bom.domain.authority.model.PubUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

*/
/**
 * Created by jindb on 2017/7/24.
 *//*

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Value("${domain}")
    private  String DOMAIN;
    @Value("${login.cookie.name}")
    private String COOKIENAME;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {
        //获得授权后可得到用户信息   可使用SUserService进行数据库操作
        PubUserInfo userDetails = (PubUserInfo)authentication.getPrincipal();
        String cookieValue=userDetails.getLogin_code()+"|" + userDetails.getPasswd();
        Cookie cookie = new Cookie(COOKIENAME,cookieValue);
        //cookie.setDomain(DOMAIN);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*12);
        response.addCookie(cookie);
       */
/* Set<SysRole> roles = userDetails.getSysRoles();*//*

        //输出登录提示信息
        System.out.println("管理员 " + userDetails.getUser_name() + " 登录");

        System.out.println("IP :"+getIpAddress(request));
        //response.sendRedirect("http://www.ueicloud.com/NewStation/StattionList.aspx");
//        response.sendRedirect("/analysis/index");
        super.onAuthenticationSuccess(request, response, authentication);
    }

    public String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}

*/
