package com.logdata.logcat.repository;

import com.logdata.logcat.model.CrashVO;
import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CrashDataRepository extends MongoRepository<CrashVO, String> {
    CrashVO findCrashDataBy(Sort sort);
    CrashVO findCrashDataByTime(DateTime time);
    CrashVO findCrashDataByOrderByTimeDescPackageName(String packageName);
}
