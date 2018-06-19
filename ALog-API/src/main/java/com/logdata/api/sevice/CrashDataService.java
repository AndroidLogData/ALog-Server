package com.logdata.api.sevice;

import com.logdata.common.model.CrashVO;
import com.logdata.api.repository.CrashDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrashDataService {
    private CrashDataRepository crashDataRepository;

    @Autowired
    public CrashDataService(CrashDataRepository crashDataRepository) {
        this.crashDataRepository = crashDataRepository;
    }

    public void save(CrashVO crashVO) {
        this.crashDataRepository.save(crashVO);
    }

    public CrashVO findCrashVOByPackageNameOrderByTimeDesc(String packageName) {
        return this.crashDataRepository.findCrashVOByPackageNameOrderByTimeDesc(packageName);
    }

    public CrashVO findCrashVOByPackageNameAndTime(String packageName, long time) {
        return this.crashDataRepository.findCrashVOByPackageNameAndTime(packageName, time);
    }

    public List<CrashVO> findByPackageNameOrderByTimeAsc(String packageName) {
        return this.crashDataRepository.findByPackageNameOrderByTimeAsc(packageName);
    }

    public List<CrashVO> findByPackageNameOrderByTimeDesc(String packageName) {
        return this.crashDataRepository.findByPackageNameOrderByTimeDesc(packageName);
    }

    public void delete(String packageName) {
        this.crashDataRepository.deleteCrashVOByPackageName(packageName);
    }
}
