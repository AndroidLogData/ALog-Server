package com.logdata.api.sevice;

import com.logdata.common.model.PackageNameVO;
import com.logdata.common.repository.PackageNameDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageNameDataService {
    private final PackageNameDataRepository packageNameDataRepository;

    @Autowired
    public PackageNameDataService(PackageNameDataRepository packageNameDataRepository) {
        this.packageNameDataRepository = packageNameDataRepository;
    }

    public List<PackageNameVO> findByApiKey(String apiKey) {
//        return packageNameDataRepository.findByApiKey(apiKey);
        return null;
    }

    public void insertPackageName(String apiKey, String packageName) {
        PackageNameVO packageNameVO = packageNameDataRepository.findByApiKey(apiKey);
        packageNameVO.setPackageName(packageName);
        packageNameDataRepository.save(packageNameVO);
    }

    public void insertUserApiKey(String apiKey) {
        packageNameDataRepository.save(new PackageNameVO(apiKey));
    }

    public PackageNameVO findPackageNameVOByApiKey(String apiKey) {
        return packageNameDataRepository.findPackageNameVOByApiKey(apiKey);
    }
}
