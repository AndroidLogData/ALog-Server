package com.logdata.api.sevice;

import com.logdata.common.model.LogVO;
import com.logdata.common.repository.LogDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogDataService {
    private LogDataRepository logDataRepository;

    @Autowired
    public LogDataService(LogDataRepository logDataRepository) {
        this.logDataRepository = logDataRepository;
    }

    public LogVO findOne(String id) {
        return this.logDataRepository.findOne(id);
    }

    public void save(LogVO logVO) {
        this.logDataRepository.save(logVO);
    }

    public List<LogVO> findByApiKeyAndPackageNameAndLevel(String apiKey, String packageName, String level, Sort sort) {
        return this.logDataRepository.findByApiKeyAndPackageNameAndLevel(apiKey, packageName, level, sort);
    }

    public List<LogVO> findByApiKeyAndPackageNameAndTag(String apiKey, String packageName, String tag, Sort sort) {
        return this.logDataRepository.findByApiKeyAndPackageNameAndTag(apiKey, packageName, tag, sort);
    }

    public List<LogVO> findByApiKeyAndPackageName(String apiKey, String packageName, Sort sort) {
        return this.logDataRepository.findByApiKeyAndPackageName(apiKey, packageName, sort);
    }

    public List<LogVO> findByPackageName(String packageName) {
        return this.logDataRepository.findByPackageName(packageName);
    }

    public List<LogVO> findByApiKey(String apiKey, Sort sort) {
        return this.logDataRepository.findByApiKey(apiKey, sort);
    }

    public List<LogVO> findByPackageNameAndApiKey(String packageName, String apiKey, Sort sort) {
        return this.logDataRepository.findByPackageNameAndApiKey(packageName, apiKey, sort);
    }

    public List<LogVO> findByPackageNameAndApiKey(String packageName, String apiKey) {
        return this.logDataRepository.findByPackageNameAndApiKey(packageName, apiKey);
    }

    public List<LogVO> findByApiKeyAndTag(String apiKey, String tag) {
        return this.logDataRepository.findByApiKeyAndTag(apiKey, tag);
    }

    public List<LogVO> findByApiKeyAndPackageName(String apiKey, String packageName) {
        return this.logDataRepository.findByApiKeyAndPackageName(apiKey, packageName);
    }

    public List<LogVO> findByApiKey(String apiKey) {
        return this.logDataRepository.findByApiKey(apiKey);
    }

    public List<LogVO> findByApiKeyAndPackageNameAndLevel(String apiKey, String packageName, String level) {
        return this.logDataRepository.findByApiKeyAndPackageNameAndLevel(apiKey, packageName, level);
    }
}
