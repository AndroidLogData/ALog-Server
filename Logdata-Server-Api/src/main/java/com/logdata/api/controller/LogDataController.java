//package com.logdata.api.controller;
//
//import com.logdata.api.service.LogDataService;
//import com.logdata.api.service.UserService;
//import com.logdata.common.model.LogDataListResponse;
//import com.logdata.common.model.LogVO;
//import com.logdata.common.model.SetDataListResponse;
//import com.logdata.common.model.UserVO;
//import com.logdata.common.util.Utility;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//import java.util.*;
//
//@Controller
//public class LogDataController {
//    @Autowired
//    private LogDataService logDataService;
//    @Autowired
//    private UserService userService;
//
//    @RequestMapping(value = "/logdata", method = RequestMethod.GET)
//    public String logDataView() {
//        return "logdata";
//    }
//
//    @RequestMapping(value = "/logdata", method = RequestMethod.POST)
//    public ResponseEntity<Map<String, String>> logDataSave(@RequestHeader(value = "secretKey") String secretKey, @RequestBody LogVO data) {
//        this.logDataService.save(new LogVO(data.getPackageName(),
//                data.getLevel(),
//                data.getTag(),
//                data.getMessage(),
//                data.getTime(),
//                data.getMemoryInfo(),
//                secretKey));
//
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
//        Map<String, String> result = new HashMap<String, String>();
//        result.put("result", "Log Data Transfer Success");
//
//        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/logdatalist", method = RequestMethod.GET, produces = "application/json")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    public LogDataListResponse list(Principal user) {
//        List<LogVO> logVOList = this.logDataService.findByApiKey(getUserApiKey(user), new Sort(Sort.Direction.DESC, "time"));
//
//        for (LogVO data : logVOList) {
//            data.setStringTime(Utility.getTime(data.getTime()));
//        }
//        return new LogDataListResponse(logVOList);
//    }
//
//    @RequestMapping(value = "/logdatalevelfilter/{query}", method = RequestMethod.GET, produces = "application/json")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    public LogDataListResponse logDataLevelList(Principal user, @RequestParam(value = "packagename") String packageName, @RequestParam(value = "level") String level) {
//        List<LogVO> logVOList = this.logDataService.findByApiKeyAndPackageNameAndLevel(getUserApiKey(user), packageName, level, new Sort(Sort.Direction.DESC, "time"));
//
//        for (LogVO data : logVOList) {
//            data.setStringTime(Utility.getTime(data.getTime()));
//        }
//        return new LogDataListResponse(logVOList);
//    }
//
//    @RequestMapping(value = "/logdatatagfilter/{query}", method = RequestMethod.GET, produces = "application/json")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    public LogDataListResponse logDataTagList(Principal user, @RequestParam(value = "packagename") String packageName, @RequestParam(value = "tag") String tag) {
//        List<LogVO> logVOList = this.logDataService.findByApiKeyAndPackageNameAndTag(getUserApiKey(user), packageName, tag, new Sort(Sort.Direction.DESC, "time"));
//
//        for (LogVO data : logVOList) {
//            data.setStringTime(Utility.getTime(data.getTime()));
//        }
//        return new LogDataListResponse(logVOList);
//    }
//
//    @RequestMapping(value = "/logdatapackagenamefilter/{packagename}", method = RequestMethod.GET, produces = "application/json")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    public LogDataListResponse logDataPackageNameList(Principal user, @RequestParam(value = "packagename") String packageName) {
//        List<LogVO> logVOList = this.logDataService.findByApiKeyAndPackageName(getUserApiKey(user), packageName, new Sort(Sort.Direction.DESC, "time"));
//
//        for (LogVO data : logVOList) {
//            data.setStringTime(Utility.getTime(data.getTime()));
//        }
//        return new LogDataListResponse(logVOList);
//    }
//
//    @RequestMapping(value = "/tagdatalist", method = RequestMethod.GET, produces = "application/json")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    private SetDataListResponse getTag(Principal user) {
//        List<LogVO> setData = this.logDataService.findByApiKey(getUserApiKey(user));
//
//        Set<String> tagSet = new HashSet<String>();
//
//        for (LogVO data : setData) {
//            tagSet.add(data.getTag());
//        }
//
//        return new SetDataListResponse(tagSet);
//    }
//
//    @RequestMapping(value = "/packagenamedatalist", method = RequestMethod.GET, produces = "application/json")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    private SetDataListResponse getPackageName(Principal user) {
//        List<LogVO> setData = this.logDataService.findByApiKey(getUserApiKey(user));
//
//        Set<String> packageNameSet = new HashSet<String>();
//
//        for (LogVO data : setData) {
//            packageNameSet.add(data.getPackageName());
//        }
//
//        return new SetDataListResponse(packageNameSet);
//    }
//
//    public String getUserApiKey(Principal user) {
//        UserVO u = this.userService.findByUserID(user.getName());
//        return u.getApiKey();
//    }
//
//    @RequestMapping(value = "/packagenamefilter/{packageName}", method = RequestMethod.GET)
//    public String logDataPackageNameView(Principal user) {
//        if (user == null) {
//            return "login";
//        }
//        return "index";
//    }
//}
