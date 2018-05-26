package com.logdata.common.repository;

import com.logdata.common.model.UserVO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserDataRepository extends MongoRepository<UserVO, String> {
    UserVO findByUserID(String name);
    UserVO findByApiKey(String apiKey);
}
