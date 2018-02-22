//package com.logdata.api.controller;
//
//import com.logdata.api.service.CrashDataService;
//import com.logdata.api.service.LogDataService;
//import com.logdata.api.service.UserService;
//import com.logdata.common.model.*;
//import com.logdata.common.util.Utility;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Controller
//public class MainController {
//    @Autowired
//    private LogDataService logDataService;
//    @Autowired
//    private CrashDataService crashDataService;
//    @Autowired
//    private UserService userService;
//
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String index() {
//        return "index";
//    }
//
//    private Set<String> getPackageName(String apiKey) {
//        List<LogVO> logData = this.logDataService.findByApiKey(apiKey, new Sort(Sort.Direction.DESC, "time"));
//
//        Set<String> packageNameSet = new HashSet<String>();
//
//        for (LogVO data : logData) {
//            packageNameSet.add(data.getPackageName());
//        }
//
//        return packageNameSet;
//    }
//
//    @RequestMapping(value = "/main", method = RequestMethod.GET, produces = "application/json")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    public MainPageDataListResponse mainPageDataList(Principal user) {
//        if (user == null) {
//            return null;
//        }
//
//        Set<String> logData = getPackageName(getUserApiKey(user));
//        List<MainPageVO> list = new ArrayList<MainPageVO>();
//
//        for (String packageName : logData) {
//            int verbCount = this.logDataService.findByApiKeyAndPackageNameAndLevel(getUserApiKey(user), packageName, "v").size();
//            int infoCount = this.logDataService.findByApiKeyAndPackageNameAndLevel(getUserApiKey(user), packageName, "i").size();
//            int debugCount = this.logDataService.findByApiKeyAndPackageNameAndLevel(getUserApiKey(user), packageName, "d").size();
//            int warningCount = this.logDataService.findByApiKeyAndPackageNameAndLevel(getUserApiKey(user), packageName, "w").size();
//            int errorCount = this.logDataService.findByApiKeyAndPackageNameAndLevel(getUserApiKey(user), packageName, "e").size();
//
//            CrashVO crashTime = this.crashDataService.findByPackageNameAndApiKeyOrderByTimeDesc(packageName, getUserApiKey(user));
//            if (crashTime == null) {
//                list.add(new MainPageVO(packageName, null, verbCount, infoCount, debugCount, warningCount, errorCount));
//            } else {
//                list.add(new MainPageVO(packageName, Utility.getTime(crashTime.getTime()), verbCount, infoCount, debugCount, warningCount, errorCount));
//            }
//        }
//
//        return new MainPageDataListResponse(list);
//    }
//
//    public String getUserApiKey(Principal user) {
//        UserVO u = this.userService.findByUserID(user.getName());
//        return u.getApiKey();
//    }
//}
