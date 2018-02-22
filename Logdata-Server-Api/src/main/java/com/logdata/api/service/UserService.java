package com.logdata.api.service;

import com.logdata.common.model.UserVO;
import com.logdata.common.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDataRepository userDataRepository;

    @Autowired
    public UserService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public void save(UserVO userVO) {
        this.userDataRepository.save(userVO);
    }

    public UserVO findByApiKey(String apiKey) {
        return this.userDataRepository.findByApiKey(apiKey);
    }

    public UserVO findByUserID(String name) {
        return this.userDataRepository.findByUserID(name);
    }
}
