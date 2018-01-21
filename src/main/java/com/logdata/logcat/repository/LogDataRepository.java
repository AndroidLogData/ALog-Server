package com.logdata.logcat.repository;

import com.logdata.logcat.model.LogData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogDataRepository extends MongoRepository<LogData, String> {
    List<LogData> findByLevel(String level);
    List<LogData> findByTag(String tag);
    List<LogData> findByPackageName(String packageName);
}
