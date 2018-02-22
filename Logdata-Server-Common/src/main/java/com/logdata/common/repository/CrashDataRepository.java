package com.logdata.common.repository;

import com.logdata.common.model.CrashVO;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@EnableMongoRepositories
public interface CrashDataRepository extends MongoRepository<CrashVO, String> {
    CrashVO findCrashDataByPackageNameAndApiKeyOrderByTimeDesc(String packageName, String apiKey);
    CrashVO findCrashDataByTimeAndApiKey(DateTime time, String apiKey);
    CrashVO findCrashDataByTimeAndApiKeyAndPackageName(DateTime time, String apiKey, String packageName);
    CrashVO findCrashDataByOrderByTimeDescPackageName(String packageName);
    CrashVO findByPackageNameAndApiKeyOrderByTimeDesc(String packageName, String apiKey);
    ArrayList<CrashVO> findByApiKeyOrderByTimeAsc(String apiKey);
    ArrayList<CrashVO> findByApiKeyAndPackageNameOrderByTimeAsc(String apiKey, String packageName);
    List<CrashVO> findByApiKey(String apiKey);
    ArrayList<CrashVO> findAllByApiKeyOrderByTimeDesc(String apiKey);
}
