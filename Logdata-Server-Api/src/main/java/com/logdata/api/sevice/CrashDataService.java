package com.logdata.api.sevice;

import com.logdata.common.model.CrashVO;
import com.logdata.common.repository.CrashDataRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrashDataService {
    private CrashDataRepository crashDataRepository;

    @Autowired
    public CrashDataService(CrashDataRepository crashDataRepository) {
        this.crashDataRepository = crashDataRepository;
    }

    public CrashVO findOne(String id) {
        return this.crashDataRepository.findOne(id);
    }

    public CrashVO findCrashDataByPackageNameAndApiKeyOrderByTimeDesc(String packageName, String apiKey) {
        return this.crashDataRepository.findCrashDataByPackageNameAndApiKeyOrderByTimeDesc(packageName, apiKey);
    }

    public CrashVO findCrashDataByTimeAndApiKey(DateTime time, String apiKey) {
        return this.crashDataRepository.findCrashDataByTimeAndApiKey(time, apiKey);
    }

    public CrashVO findCrashDataByTimeAndApiKeyAndPackageName(long time, String apiKey, String packageName) {
        return this.crashDataRepository.findCrashDataByTimeAndApiKeyAndPackageName(time, apiKey, packageName);
    }

    public CrashVO findCrashDataByOrderByTimeDescPackageName(String packageName) {
        return this.crashDataRepository.findCrashDataByOrderByTimeDescPackageName(packageName);
    }

    public CrashVO findByPackageNameAndApiKeyOrderByTimeDesc(String packageName, String apiKey) {
        return this.crashDataRepository.findByPackageNameAndApiKeyOrderByTimeDesc(packageName, apiKey);
    }

    public ArrayList<CrashVO> findByApiKeyOrderByTimeAsc(String apiKey) {
        return this.crashDataRepository.findByApiKeyOrderByTimeAsc(apiKey);
    }

    public ArrayList<CrashVO> findByApiKeyAndPackageNameOrderByTimeAsc(String apiKey, String packageName) {
        return this.crashDataRepository.findByApiKeyAndPackageNameOrderByTimeAsc(apiKey, packageName);
    }

    public List<CrashVO> findByApiKey(String apiKey) {
        return this.crashDataRepository.findByApiKey(apiKey);
    }

    public ArrayList<CrashVO> findAllByApiKeyOrderByTimeDesc(String apiKey) {
        return this.crashDataRepository.findAllByApiKeyOrderByTimeDesc(apiKey);
    }

    public void save(CrashVO crashVO) {
        this.crashDataRepository.save(crashVO);
    }
}
