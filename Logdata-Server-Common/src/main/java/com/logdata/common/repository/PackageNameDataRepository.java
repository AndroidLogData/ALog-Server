package com.logdata.common.repository;

import com.logdata.common.model.PackageNameVO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PackageNameDataRepository extends MongoRepository<PackageNameVO, String> {
//    List<PackageNameVO> findByApiKey(String apiKey);
    PackageNameVO findByApiKey(String apiKey);
    PackageNameVO findPackageNameVOByApiKey(String apiKey);
}
