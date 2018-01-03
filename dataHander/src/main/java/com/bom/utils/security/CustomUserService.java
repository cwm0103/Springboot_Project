/*
package com.bom.utils.security;


import com.bom.domain.authority.model.PubRoleInfo;
import com.bom.domain.authority.model.PubUserInfo;
import com.bom.domain.authority.service.IPubUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

*/
/**
 * Created by jindb on 2017/7/24.
 *
 * @author: jindb
 * @date: 2017/7/24 13:50
 * @description:
 *//*

@Component
public class CustomUserService implements UserDetailsService {

    @Autowired  //业务服务类
    private IPubUserInfoService _userinfoService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        */
/*ServletRequestAttributes requestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        *//*

        PubUserInfo user=_userinfoService.GetUserByLoginCode(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户： " +  username+ " 不存在！");
        }
        SecurityUser seu = new SecurityUser(user);
//        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();//GrantedAuthority是security提供的权限类，
//
//        getRoles(user,list);//获取角色，放到list里面
//
//        org.springframework.security.core.userdetails.User auth_user = new
//                org.springframework.security.core.userdetails.User(user.getUser_name(),user.getPasswd(),list);//返回包括权限角色的User给security
        return  seu;
    }

    public  static SecurityUser GetCurrUser(){
       return   (SecurityUser) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();

    }
    */
/**
     * 获取所属角色
     * @param user
     * @param list
     *//*

    public void getRoles(PubUserInfo user, List<GrantedAuthority> list){
        for (PubRoleInfo role:user.getRoles()) {
            //权限如果前缀是ROLE_，security就会认为这是个角色信息，而不是权限，例如ROLE_MENBER就是MENBER角色，CAN_SEND就是CAN_SEND权限
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRole_id()));
        }
    }
}
*/
