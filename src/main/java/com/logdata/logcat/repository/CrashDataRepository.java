package com.logdata.logcat.repository;

import com.logdata.logcat.model.CrashData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CrashDataRepository extends MongoRepository<CrashData, String> {
}
