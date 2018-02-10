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

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String index(Principal user, Model model) {
//        if (user == null) {
//            return "index";
//        }
//        UserVO u = this.userDataRepository.findByUserID(user.getName());
//
//        Set<String> logData = getPackageName(u.getApiKey());
//        HashMap<String, MainPageVO> map = new HashMap<String, MainPageVO>();
//
//        for (String packageName : logData) {
//            int logDataCount = this.logDataRepository.findByPackageNameAndApiKey(packageName, u.getApiKey()).size();
//            CrashVO crashTime = this.crashDataRepository.findByPackageNameAndApiKeyOrderByTimeDesc(packageName, u.getApiKey());
//            if (crashTime == null) {
//                map.put(packageName, new MainPageVO(packageName, logDataCount, null));
//            } else {
//                map.put(packageName, new MainPageVO(packageName, logDataCount, crashTime.getTime()));
//            }
//        }
//
//        model.addAttribute("packageName", logData);
//        model.addAttribute("summaryData", map);
//
//        return "index";
//    }

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

        UserVO u = this.userDataRepository.findByUserID(user.getName());

        Set<String> logData = getPackageName(u.getApiKey());
        List<MainPageVO> list = new ArrayList<MainPageVO>();

        for (String packageName : logData) {
            int logDataCount = this.logDataRepository.findByPackageNameAndApiKey(packageName, u.getApiKey()).size();
            CrashVO crashTime = this.crashDataRepository.findByPackageNameAndApiKeyOrderByTimeDesc(packageName, u.getApiKey());
            if (crashTime == null) {
                list.add(new MainPageVO(packageName, logDataCount, null));
            } else {
                list.add(new MainPageVO(packageName, logDataCount, crashTime.getTime()));
            }
        }

        return new MainPageDataListResponse(list);

//        List<LogVO> logVOList = this.logDataRepository.findByApiKey(getUserApiKey(user), new Sort(Sort.Direction.DESC, "time"));
//
//        for (LogVO data : logVOList) {
//            data.setStringTime(Utility.getTime(data.getTime()));
//        }
//        return new LogDataListResponse(logVOList);
    }

    public String getUserApiKey(Principal user) {
        UserVO u = this.userDataRepository.findByUserID(user.getName());
        return u.getApiKey();
    }
}
