package com.logdata.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logdata.common.model.LogDataListResponse;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.SetDataListResponse;
import com.logdata.common.model.UserVO;
import com.logdata.common.repository.LogDataRepository;
import com.logdata.common.repository.UserDataRepository;
import com.logdata.common.util.Utility;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.security.Principal;
import java.util.*;

@Controller
public class LogDataController {
    @Autowired
    private LogDataRepository logDataRepository;
    @Autowired
    private UserDataRepository userDataRepository;

    @RequestMapping(value = "/logdata", method = RequestMethod.GET)
    public String logDataView() {
        return "logdata";
    }

    @RequestMapping(value = "/logdata", method = RequestMethod.POST)
    public ResponseEntity<Object> logDataSave(@RequestHeader(value = "secretKey") String secretKey, @RequestBody LogVO data) {
        return RestAPIUtility.postData("/logdatasave", secretKey, data);
    }

    @RequestMapping(value = "/logdatalist", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataListResponse list(Principal user) {
        List<LogVO> logVOList = this.logDataRepository.findByApiKey(getUserApiKey(user), new Sort(Sort.Direction.DESC, "time"));

        for (LogVO data : logVOList) {
            data.setStringTime(Utility.timeTranslate(data.getTime()));
        }
        return new LogDataListResponse(logVOList);
    }

    @RequestMapping(value = "/logdatalevelfilter/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataListResponse logDataLevelList(Principal user, @RequestParam(value = "packagename") String packageName, @RequestParam(value = "level") String level) {
        List<LogVO> logVOList = this.logDataRepository.findByApiKeyAndPackageNameAndLevel(getUserApiKey(user), packageName, level, new Sort(Sort.Direction.DESC, "time"));

        for (LogVO data : logVOList) {
            data.setStringTime(Utility.timeTranslate(data.getTime()));
        }
        return new LogDataListResponse(logVOList);
    }

    @RequestMapping(value = "/logdatatagfilter/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataListResponse logDataTagList(Principal user, @RequestParam(value = "packagename") String packageName, @RequestParam(value = "tag") String tag) {
        List<LogVO> logVOList = this.logDataRepository.findByApiKeyAndPackageNameAndTag(getUserApiKey(user), packageName, tag, new Sort(Sort.Direction.DESC, "time"));

        for (LogVO data : logVOList) {
            data.setStringTime(Utility.timeTranslate(data.getTime()));
        }
        return new LogDataListResponse(logVOList);
    }

    @RequestMapping(value = "/logdatapackagenamefilter/{packagename}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataListResponse logDataPackageNameList(Principal user, @RequestParam(value = "packagename") String packageName) {
        RestAPIUtility.getData("/logdatapackagenamefilter", "ea0cc2e630ed4b6683252eb6114f89a9", packageName);
//        System.out.println(RestAPIUtility.getData("/logdatapackagenamefilter", "ea0cc2e630ed4b6683252eb6114f89a9", packageName));
//        List<LogVO> logVOList = this.logDataRepository.findByApiKeyAndPackageName(getUserApiKey(user), packageName, new Sort(Sort.Direction.DESC, "time"));
//
//        for (LogVO data : logVOList) {
//            data.setStringTime(Utility.timeTranslate(data.getTime()));
//        }
//        return new LogDataListResponse(logVOList);
        return null;
    }

    @RequestMapping(value = "/packagenamedatalist", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    private SetDataListResponse getPackageName(@RequestHeader(value = "secretKey") String secretKey) {
        return (SetDataListResponse) RestAPIUtility.getData("/packagenamedatalist", secretKey);
//        List<LogVO> setData = this.logDataRepository.findByApiKey(secretKey);
//
//        Set<String> packageNameSet = new HashSet<String>();
//
//        for (LogVO data : setData) {
//            packageNameSet.add(data.getPackageName());
//        }
//
//        return new SetDataListResponse(packageNameSet);
    }

    @RequestMapping(value = "/logdatapackagenamefilter/{packagename}", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataListResponse logDataPackageNameList(@RequestHeader(value = "secretKey") String secretKey, @RequestParam(value = "packagename") String packageName) {
//        return (LogDataListResponse) RestAPIUtility.getData("/logdatapackagenamefilter", secretKey, packageName);
//        List<LogVO> logVOList = this.logDataRepository.findByApiKeyAndPackageName(secretKey, packageName, new Sort(Sort.Direction.DESC, "time"));
//
//        for (LogVO data : logVOList) {
//            data.setStringTime(Utility.timeTranslate(data.getTime()));
//        }
//        return new LogDataListResponse(logVOList);
        return null;
    }

    public String getUserApiKey(Principal user) {
        UserVO u = this.userDataRepository.findByUserID(user.getName());
        return u.getApiKey();
    }

    @RequestMapping(value = "/packagenamefilter/{packageName}", method = RequestMethod.GET)
    public String logDataPackageNameView(Principal user) {
        if (user == null) {
            return "login";
        }
        return "index";
    }
}
