package com.bom.constants;

import java.util.ArrayList;
import java.util.List;

//系统常量
public class Constants {

    public static final String USER_SESSION="operation_user";
    /*
     重定向后的地址
    */
    public static final String REDIRECT_URL="/";
    /*
     *使用此地址需要在拦截器中拼接重定向地址
     */
    public static final String SSO_REDIRECT_URL="http://uac-icome-dev.ipaas.enncloud.cn/login.html?redirect_uri=";
    /*
     *SESSION如果存在访问首页后跳转的地址
     */
    public static final String EXIST_SESSION_REDIRECT_URL="/analysis/index";
    /*
     系统访问的公开资源
     */
    public static final List<String> ANONYMOUS_ACTIONS = new ArrayList<String>() {{
        add("/");
        add("/authority/login");
        add("/equipmentinfo/GetEquipmentData");
        add("/error");
    }};
    /*
     *存在SESSION的情况下需要校验访问页面的URI地址
     */
    public static final List<String> EXIST_SESSION_URI=new ArrayList<String>(){{
        add("/");
        add("/login");
    }};
}
