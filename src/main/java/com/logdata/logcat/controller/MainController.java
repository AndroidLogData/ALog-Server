package com.logdata.logcat.controller;

import com.logdata.logcat.model.CrashVO;
import com.logdata.logcat.model.LogVO;
import com.logdata.logcat.model.MainPageVO;
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
        HashMap<String, MainPageVO> map = new HashMap<String, MainPageVO>();

        for (String packageName : logData) {
            int logDataCount = this.logDataRepository.findByPackageName(packageName).size();
            CrashVO crashTime = this.crashDataRepository.findCrashDataByOrderByTimeDescPackageName(packageName);
            map.put(packageName, new MainPageVO(packageName, logDataCount, crashTime.getTime()));
        }

        model.addAttribute("packageName", logData);
        model.addAttribute("summaryData", map);

        return "index";
    }

    private Set<String> getPackageName() {
        List<LogVO> logData = this.logDataRepository.findAll();

        Set<String> packageNameSet = new HashSet<String>();

        for (LogVO data : logData) {
            packageNameSet.add(data.getPackageName());
        }

        return packageNameSet;
    }
}
