package com.logdata.backend.service;

import com.logdata.backend.model.SecurityUser;
import com.logdata.common.model.UserVO;
import com.logdata.common.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO user = this.userDataRepository.findByUserID(username);
        SecurityUser securityUser = new SecurityUser(user);

        if (securityUser == null) {
            throw new UsernameNotFoundException("등록된 유저가 아닙니다.");
        }

        return securityUser;
    }
}
