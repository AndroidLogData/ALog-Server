package com.logdata.web.service;

import com.logdata.api.service.UserService;
import com.logdata.common.model.SecurityUser;
import com.logdata.common.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO user = this.userService.findByUserID(username);
        SecurityUser securityUser = new SecurityUser(user);

        if (securityUser == null) {
            throw new UsernameNotFoundException("등록된 유저가 아닙니다.");
        }

        return securityUser;
    }
}
