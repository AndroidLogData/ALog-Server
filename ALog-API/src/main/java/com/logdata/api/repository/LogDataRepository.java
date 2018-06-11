package com.logdata.api.repository;

import com.logdata.common.model.LogVO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogDataRepository extends MongoRepository<LogVO, String> {
    List<LogVO> findByPackageNameAndLevel(String packageName, String level, Sort sort);
    List<LogVO> findByPackageNameAndTag(String packageName, String tag, Sort sort);
    List<LogVO> findByPackageName(String packageName, Sort sort);
    List<LogVO> findByPackageName(String packageName);
    List<LogVO> findByPackageNameAndLevel(String packageName, String level);
    void deleteLogVOSByPackageName(String packageName);
}
