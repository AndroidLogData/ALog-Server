package com.logdata.logcat.controller;

import com.logdata.logcat.model.LogData;
import com.logdata.logcat.repository.LogDataRepository;
import com.logdata.logcat.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {
    @Autowired
    private LogDataRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        Set<String> logData = getPackageName();

        model.addAttribute("packageName", logData);

        return "index";
    }

    private Set<String> getPackageName() {
        List<LogData> logData = this.repository.findAll();

        Set<String> packageNameSet = new HashSet<String>();

        for (LogData aLogData : logData) {
            packageNameSet.add(aLogData.getPackageName());
        }

        return packageNameSet;
    }
}
