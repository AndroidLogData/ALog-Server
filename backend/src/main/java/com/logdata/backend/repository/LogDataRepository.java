package com.logdata.backend.repository;

import com.logdata.backend.model.LogVO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogDataRepository extends MongoRepository<LogVO, String> {
    List<LogVO> findByApiKeyAndLevel(String apiKey, String level, Sort sort);
    List<LogVO> findByApiKeyAndTag(String apiKey, String tag, Sort sort);
    List<LogVO> findByApiKeyAndPackageName(String apiKey, String packageName, Sort sort);
    List<LogVO> findByPackageName(String packageName);
    List<LogVO> findByApiKey(String apiKey, Sort sort);
    List<LogVO> findByPackageNameAndApiKey(String packageName, String apiKey, Sort sort);
    List<LogVO> findByPackageNameAndApiKey(String packageName, String apiKey);
    List<LogVO> findByApiKeyAndTag(String apiKey, String tag);
    List<LogVO> findByApiKeyAndPackageName(String apiKey, String packageName);
    List<LogVO> findByApiKey(String apiKey);
}
