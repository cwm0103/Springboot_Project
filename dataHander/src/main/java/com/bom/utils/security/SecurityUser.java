/*
package com.bom.utils.security;

import com.bom.domain.authority.model.PubRoleInfo;
import com.bom.domain.authority.model.PubUserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

*/
/**
 * Created by jindb on 2017/7/24.
 *
 * @author: jindb
 * @date: 2017/7/24 13:32
 * @description:
 *//*

public class SecurityUser extends PubUserInfo implements UserDetails {
    private static final long serialVersionUID = 1L;
    public SecurityUser(PubUserInfo suser) {
        if(suser != null)
        {
            this.setUser_id(suser.getUser_id());
            this.setUser_name(suser.getUser_name());
            this.setEmail(suser.getEmail());
            this.setPasswd(suser.getPasswd());
            this.setDept_id(suser.getDept_id());
            this.setFile_id(suser.getFile_id());
            this.setDisplay_no(suser.getDisplay_no());
            this.setDegree_code(suser.getDegree_code());
            this.setLogin_code(suser.getLogin_code());
            this.setRoles(suser.getRoles());

        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (PubRoleInfo role:this.getRoles()) {
            //权限如果前缀是ROLE_，security就会认为这是个角色信息，而不是权限，例如ROLE_MENBER就是MENBER角色，CAN_SEND就是CAN_SEND权限
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRole_id()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPasswd();
    }

    @Override
    public String getUsername() {
        return super.getUser_name();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
*/
