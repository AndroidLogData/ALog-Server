package com.logdata.api.repository;

import com.logdata.common.model.CrashVO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CrashDataRepository extends MongoRepository<CrashVO, String> {
    CrashVO findCrashVOByPackageNameOrderByTimeDesc(String packageName);
    CrashVO findCrashVOByPackageNameAndTime(String packageName, long time);
    List<CrashVO> findByPackageNameOrderByTimeAsc(String packageName);
    List<CrashVO> findByPackageNameOrderByTimeDesc(String packageName);
    void deleteCrashVOByPackageName(String packageName);
}
