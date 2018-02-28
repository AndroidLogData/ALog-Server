package com.logdata.api.controller;

import com.logdata.api.sevice.LogDataService;
import com.logdata.common.model.LogDataListResponse;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.SetDataListResponse;
import com.logdata.common.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/api")
public class LogDataController {
    private final LogDataService logDataService;

    @Autowired
    public LogDataController(LogDataService logDataService) {
        this.logDataService = logDataService;
    }

    @RequestMapping(value = "/logdatasave")
    public ResponseEntity<Map<String, String>> logDataSave(@RequestHeader(value = "secretKey") String secretKey, @RequestBody LogVO data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();

        if (secretKey.equals("")) {
            responseHeaders = new HttpHeaders();
            result.put("result", "Need API Key");

            return new ResponseEntity<>(result, responseHeaders, HttpStatus.FORBIDDEN);
        }

        this.logDataService.save(new LogVO(
                data.getPackageName(),
                data.getLevel(),
                data.getTag(),
                data.getMessage(),
                data.getTime(),
                data.getMemoryInfo(),
                secretKey));

        responseHeaders = new HttpHeaders();
        result.put("result", "Log Data Transfer Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/tagdatalist", method = RequestMethod.GET, produces = "application/json")
    private SetDataListResponse getTag(@RequestHeader(value = "secretKey") String secretKey) {
        List<LogVO> setData = this.logDataService.findByApiKey(secretKey);

        Set<String> tagSet = new HashSet<String>();

        for (LogVO data : setData) {
            tagSet.add(data.getTag());
        }

        return new SetDataListResponse(tagSet);
    }

    @RequestMapping(value = "/packagenamedatalist", method = RequestMethod.GET, produces = "application/json")
    private SetDataListResponse getPackageName(@RequestHeader(value = "secretKey") String secretKey) {
        List<LogVO> setData = this.logDataService.findByApiKey(secretKey);

        Set<String> packageNameSet = new HashSet<String>();

        for (LogVO data : setData) {
            packageNameSet.add(data.getPackageName());
        }

        return new SetDataListResponse(packageNameSet);
    }

    @RequestMapping(value = "/logdatapackagenamefilter/{packagename}")
    @ResponseBody
    public LogDataListResponse logDataPackageNameList(@RequestHeader(value = "secretKey") String secretKey, @RequestParam(value = "packagename") String packageName) {
        System.out.println(secretKey);
        System.out.println(packageName);
        List<LogVO> logVOList = this.logDataService.findByApiKeyAndPackageName(secretKey, packageName, new Sort(Sort.Direction.DESC, "time"));

        for (LogVO data : logVOList) {
            data.setStringTime(Utility.timeTranslate(data.getTime()));
        }

//        return logVOList;
        return new LogDataListResponse(logVOList);
    }
}
