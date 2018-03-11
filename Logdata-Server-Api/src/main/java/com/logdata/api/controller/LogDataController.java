package com.logdata.api.controller;

import com.logdata.api.sevice.LogDataService;
import com.logdata.common.model.LogVO;
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
    private final Utility utility;

    @Autowired
    public LogDataController(LogDataService logDataService, Utility utility) {
        this.logDataService = logDataService;
        this.utility = utility;
    }

    @RequestMapping(value = "/logdata", method = RequestMethod.POST)
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

    @RequestMapping(value = "/logdata/filter/level/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<LogVO> logDataLevelList(@RequestHeader(value = "secretKey") String secretKey, @RequestParam(value = "packagename") String packageName, @RequestParam(value = "level") String level) {
        List<LogVO> logVOList = this.logDataService.findByApiKeyAndPackageNameAndLevel(secretKey, packageName, level, new Sort(Sort.Direction.DESC, "time"));

        for (LogVO data : logVOList) {
            data.setStringTime(utility.getStringTimeToLong(data.getTime()));
        }

        return logVOList;
    }

    @RequestMapping(value = "/logdata/filter/tag/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<LogVO> logDataTagList(@RequestHeader(value = "secretKey") String secretKey, @RequestParam(value = "packagename") String packageName, @RequestParam(value = "tag") String tag) {
        List<LogVO> logVOList = this.logDataService.findByApiKeyAndPackageNameAndTag(secretKey, packageName, tag, new Sort(Sort.Direction.DESC, "time"));

        for (LogVO data : logVOList) {
            data.setStringTime(utility.getStringTimeToLong(data.getTime()));
        }

        return logVOList;
    }

    @RequestMapping(value = "/logdata/tag/set", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    private Set<String> getTag(@RequestHeader(value = "secretKey") String secretKey) {
        List<LogVO> setData = this.logDataService.findByApiKey(secretKey);

        Set<String> tagSet = new HashSet<String>();

        for (LogVO data : setData) {
            tagSet.add(data.getTag());
        }

        return tagSet;
    }

    @RequestMapping(value = "/logdata/packagename/set", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    private Set<String> getPackageName(@RequestHeader(value = "secretKey") String secretKey) {
        List<LogVO> setData = this.logDataService.findByApiKey(secretKey);

        Set<String> packageNameSet = new HashSet<String>();

        for (LogVO data : setData) {
            packageNameSet.add(data.getPackageName());
        }

        return packageNameSet;
    }

    @RequestMapping(value = "/logdata/filter/packagename/{packagename}")
    @ResponseBody
    public List<LogVO> logDataPackageNameList(@RequestHeader(value = "secretKey") String secretKey, @RequestParam(value = "packagename") String packageName) {
        List<LogVO> logVOList = this.logDataService.findByApiKeyAndPackageName(secretKey, packageName, new Sort(Sort.Direction.DESC, "time"));

        for (LogVO data : logVOList) {
            data.setStringTime(utility.getStringTimeToLong(data.getTime()));
        }

        return logVOList;
//        return new LogDataListResponse(logVOList);
    }

    @RequestMapping(value = "/logdata/list", method = RequestMethod.GET)
    @ResponseBody
    public List<LogVO> allLogData() {
        return this.logDataService.findAll();
    }
}
