package com.logdata.logcat.controller;

import com.logdata.logcat.model.CrashVO;
import com.logdata.logcat.model.LogVO;
import com.logdata.logcat.model.MainPageVO;
import com.logdata.logcat.model.UserVO;
import com.logdata.logcat.repository.CrashDataRepository;
import com.logdata.logcat.repository.LogDataRepository;
import com.logdata.logcat.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
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
    @Autowired
    private UserDataRepository userDataRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal user, Model model) {
        if (user == null) {
            return "index";
        }
        UserVO u = this.userDataRepository.findByUserID(user.getName());

        Set<String> logData = getPackageName(u.getApiKey());
        HashMap<String, MainPageVO> map = new HashMap<String, MainPageVO>();

        for (String packageName : logData) {
            int logDataCount = this.logDataRepository.findByPackageNameAndApiKey(packageName, u.getApiKey()).size();
            CrashVO crashTime = this.crashDataRepository.findByPackageNameAndApiKeyOrderByTimeDesc(packageName, u.getApiKey());
            if (crashTime == null) {
                map.put(packageName, new MainPageVO(packageName, logDataCount, null));
            } else {
                map.put(packageName, new MainPageVO(packageName, logDataCount, crashTime.getTime()));
            }
        }

        model.addAttribute("packageName", logData);
        model.addAttribute("summaryData", map);

        return "index";
    }

    private Set<String> getPackageName(String apiKey) {
        List<LogVO> logData = this.logDataRepository.findByApiKey(apiKey, new Sort(Sort.Direction.DESC, "time"));

        Set<String> packageNameSet = new HashSet<String>();

        for (LogVO data : logData) {
            packageNameSet.add(data.getPackageName());
        }

        return packageNameSet;
    }
}
