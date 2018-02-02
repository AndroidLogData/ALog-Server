package com.logdata.logcat.repository;

import com.logdata.logcat.model.CrashVO;
import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CrashDataRepository extends MongoRepository<CrashVO, String> {
    CrashVO findCrashDataBy(Sort sort);
    CrashVO findCrashDataByTime(DateTime time);
    CrashVO findCrashDataByOrderByTimeDescPackageName(String packageName);
    CrashVO findByPackageNameAndApiKeyOrderByTimeDesc(String packageName, String apiKey);
    List<CrashVO> findByApiKeyOrderByTimeDesc(String apiKey, Sort sort);
}
