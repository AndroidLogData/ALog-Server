package com.logdata.backend.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

public class SecurityUser extends User {
    private static final String ROLE_PREFIX = "ROLE_";
    private static final long serialVersionUID = 1L;

    public SecurityUser(UserVO user) {
        super(user.getUserID(), user.getPassword(), makeGrantedAuthority(user.getRoles()));
    }

    private static List<GrantedAuthority> makeGrantedAuthority(List<UserRoles> roles){
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));
        return list;
    }
}
