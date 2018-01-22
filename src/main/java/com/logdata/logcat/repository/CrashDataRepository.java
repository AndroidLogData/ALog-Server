package com.logdata.logcat.repository;

import com.logdata.logcat.model.CrashData;
import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CrashDataRepository extends MongoRepository<CrashData, String> {
    CrashData findCrashDataBy(Sort sort);
    CrashData findCrashDataByTime(DateTime time);
}
