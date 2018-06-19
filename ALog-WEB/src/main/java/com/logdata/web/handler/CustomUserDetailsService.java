package com.logdata.web.handler;

import com.logdata.common.model.UserVO;
import com.logdata.web.model.SecurityUser;
import com.logdata.web.service.RestAPIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final RestAPIManager restAPIManager;

    @Autowired
    public CustomUserDetailsService(RestAPIManager restAPIManager) {
        this.restAPIManager = restAPIManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO user = this.restAPIManager.findUser(username);

        if (user == null) {
            throw new UsernameNotFoundException("등록된 유저가 아닙니다.");
        }

        return new SecurityUser(user);
    }
}
