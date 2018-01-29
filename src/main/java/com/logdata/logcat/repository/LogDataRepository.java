package com.logdata.logcat.repository;

import com.logdata.logcat.model.LogVO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogDataRepository extends MongoRepository<LogVO, String> {
    List<LogVO> findByLevel(String level, Sort sort);
    List<LogVO> findByTag(String tag, Sort sort);
    List<LogVO> findByPackageName(String packageName, Sort sort);
    List<LogVO> findByPackageName(String packageName);
    List<LogVO> findByApiKey(String apiKey, Sort sort);
}
