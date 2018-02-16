package com.logdata.backend.repository;

import com.logdata.backend.model.CrashVO;
import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;

public interface CrashDataRepository extends MongoRepository<CrashVO, String> {
    CrashVO findCrashDataBy(Sort sort);
    CrashVO findCrashDataByTimeAndApiKey(DateTime time, String apiKey);
    CrashVO findCrashDataByOrderByTimeDescPackageName(String packageName);
    CrashVO findByPackageNameAndApiKeyOrderByTimeDesc(String packageName, String apiKey);
    List<CrashVO> findByApiKeyOrderByTimeDesc(String apiKey, Sort sort);
    ArrayList<CrashVO> findByApiKeyOrderByTimeAsc(String apiKey);
    ArrayList<CrashVO> findByApiKeyAndPackageNameOrderByTimeAsc(String apiKey, String packageName);
    List<CrashVO> findByApiKey(String apiKey);
}
