package com.logdata.logcat.repository;

import com.logdata.logcat.model.LogData;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogDataRepository extends MongoRepository<LogData, String> {
    List<LogData> findByLevel(String level, Sort sort);
    List<LogData> findByTag(String tag, Sort sort);
    List<LogData> findByPackageName(String packageName, Sort sort);
}
