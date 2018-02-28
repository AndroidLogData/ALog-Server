package com.logdata.common.repository;

import com.logdata.common.model.LogVO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogDataRepository extends MongoRepository<LogVO, String> {
    List<LogVO> findByApiKeyAndPackageNameAndLevel(String apiKey, String packageName, String level, Sort sort);
    List<LogVO> findByApiKeyAndPackageNameAndTag(String apiKey, String packageName, String tag, Sort sort);
    List<LogVO> findByApiKeyAndPackageName(String apiKey, String packageName, Sort sort);
    List<LogVO> findByPackageName(String packageName);
    List<LogVO> findByApiKey(String apiKey, Sort sort);
    List<LogVO> findByPackageNameAndApiKey(String packageName, String apiKey, Sort sort);
    List<LogVO> findByPackageNameAndApiKey(String packageName, String apiKey);
    List<LogVO> findByApiKeyAndTag(String apiKey, String tag);
    List<LogVO> findByApiKeyAndPackageName(String apiKey, String packageName);
    List<LogVO> findByApiKey(String apiKey);
    List<LogVO> findByApiKeyAndPackageNameAndLevel(String apiKey, String packageName, String level);
}
