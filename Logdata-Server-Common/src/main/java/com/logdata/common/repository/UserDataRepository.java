package com.logdata.common.repository;

import com.logdata.common.model.UserVO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDataRepository extends MongoRepository<UserVO, String> {
    UserVO findByUserID(String name);
    UserVO findByApiKey(String apiKey);
}
