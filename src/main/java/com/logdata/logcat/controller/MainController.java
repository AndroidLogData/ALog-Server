package com.logdata.logcat.controller;

import com.logdata.logcat.model.CrashData;
import com.logdata.logcat.model.LogData;
import com.logdata.logcat.model.MainPageData;
import com.logdata.logcat.repository.CrashDataRepository;
import com.logdata.logcat.repository.LogDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {
    @Autowired
    private LogDataRepository logDataRepository;
    @Autowired
    private CrashDataRepository crashDataRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        Set<String> logData = getPackageName();
        HashMap<String, MainPageData> map = new HashMap<String, MainPageData>();

        for (String packageName : logData) {
            int logDataCount = this.logDataRepository.findByPackageName(packageName).size();
            CrashData crashTime = this.crashDataRepository.findCrashDataByOrderByTimeDescPackageName(packageName);
            map.put(packageName, new MainPageData(packageName, logDataCount, crashTime.getTime()));
        }

        model.addAttribute("packageName", logData);
        model.addAttribute("summaryData", map);

        return "index";
    }

    private Set<String> getPackageName() {
        List<LogData> logData = this.logDataRepository.findAll();

        Set<String> packageNameSet = new HashSet<String>();

        for (LogData aLogData : logData) {
            packageNameSet.add(aLogData.getPackageName());
        }

        return packageNameSet;
    }
}
