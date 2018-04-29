package com.logdata.api.sevice;

import com.logdata.common.model.UserVO;
import com.logdata.common.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataService {
    private UserDataRepository userDataRepository;

    @Autowired
    public UserDataService(UserDataRepository userDataRepository) {
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

    public List<UserVO> findAllByApiKey() {
        return this.userDataRepository.findAllByApiKey();
    }
}
