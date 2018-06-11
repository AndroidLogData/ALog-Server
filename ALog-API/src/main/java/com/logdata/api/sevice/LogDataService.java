package com.logdata.api.sevice;

import com.logdata.common.model.LogVO;
import com.logdata.api.repository.LogDataRepository;
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

    public void delete(String packageName) {
        this.logDataRepository.deleteLogVOSByPackageName(packageName);
    }

    public List<LogVO> findAll() {
        return this.logDataRepository.findAll();
    }

    public List<LogVO> findAll(Sort sort) {
        return this.logDataRepository.findAll(sort);
    }

    public List<LogVO> findByPackageNameAndLevel(String packageName, String level, Sort sort) {
        return this.logDataRepository.findByPackageNameAndLevel(packageName, level, sort);
    }

    public List<LogVO> findByPackageNameAndTag(String packageName, String tag, Sort sort) {
        return this.logDataRepository.findByPackageNameAndTag(packageName, tag, sort);
    }

    public List<LogVO> findByPackageName(String packageName, Sort sort) {
        return this.logDataRepository.findByPackageName(packageName, sort);
    }

    public List<LogVO> findByPackageName(String packageName) {
        return this.logDataRepository.findByPackageName(packageName);
    }

    public List<LogVO> findByPackageNameAndLevel(String packageName, String level) {
        return this.logDataRepository.findByPackageNameAndLevel(packageName, level);
    }
}
