package com.logdata.backend.controller;

import com.logdata.backend.model.*;
import com.logdata.backend.repository.CrashDataRepository;
import com.logdata.backend.repository.LogDataRepository;
import com.logdata.backend.repository.UserDataRepository;
import com.logdata.backend.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private LogDataRepository logDataRepository;
    @Autowired
    private CrashDataRepository crashDataRepository;
    @Autowired
    private UserDataRepository userDataRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
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

    @RequestMapping(value = "/main", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public MainPageDataListResponse mainPageDataList(Principal user) {
        if (user == null) {
            return null;
        }

        Set<String> logData = getPackageName(getUserApiKey(user));
        List<MainPageVO> list = new ArrayList<MainPageVO>();

        for (String packageName : logData) {
            int verbCount = this.logDataRepository.findByApiKeyAndPackageNameAndLevel(getUserApiKey(user), packageName, "v").size();
            int infoCount = this.logDataRepository.findByApiKeyAndPackageNameAndLevel(getUserApiKey(user), packageName, "i").size();
            int debugCount = this.logDataRepository.findByApiKeyAndPackageNameAndLevel(getUserApiKey(user), packageName, "d").size();
            int warningCount = this.logDataRepository.findByApiKeyAndPackageNameAndLevel(getUserApiKey(user), packageName, "w").size();
            int errorCount = this.logDataRepository.findByApiKeyAndPackageNameAndLevel(getUserApiKey(user), packageName, "e").size();

            CrashVO crashTime = this.crashDataRepository.findByPackageNameAndApiKeyOrderByTimeDesc(packageName, getUserApiKey(user));
            if (crashTime == null) {
                list.add(new MainPageVO(packageName, null, verbCount, infoCount, debugCount, warningCount, errorCount));
            } else {
                list.add(new MainPageVO(packageName, Utility.getTime(crashTime.getTime()), verbCount, infoCount, debugCount, warningCount, errorCount));
            }
        }

        return new MainPageDataListResponse(list);
    }

    public String getUserApiKey(Principal user) {
        UserVO u = this.userDataRepository.findByUserID(user.getName());
        return u.getApiKey();
    }
}
