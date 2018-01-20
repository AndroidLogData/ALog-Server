package com.logdata.logcat.repository;

import com.logdata.logcat.model.LogData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogDataRepository extends MongoRepository<LogData, String> {
}
