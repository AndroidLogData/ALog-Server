package com.logdata.api.controller;

import com.logdata.api.sevice.LogDataService;
import com.logdata.api.sevice.PackageNameDataService;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.PackageNameVO;
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
    private final PackageNameDataService packageNameDataService;

    @Autowired
    public LogDataController(LogDataService logDataService, PackageNameDataService packageNameDataService) {
        this.logDataService = logDataService;
        this.packageNameDataService = packageNameDataService;
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

        this.logDataService.save(
                new LogVO(
                        data.getPackageName(),
                        data.getLevel(),
                        data.getTag(),
                        data.getMessage(),
                        data.getTime(),
                        data.getMemoryInfo()
                )
        );

        PackageNameVO packageNameList = packageNameDataService.findPackageNameVOByApiKey(secretKey);

        if (packageNameList.getPackageNameList().size() == 0) {
            packageNameDataService.insertPackageName(secretKey, data.getPackageName());
        } else {
            boolean flag = false;
            for (int i = 0; i < packageNameList.getPackageNameList().size(); i++) {
                if (data.getPackageName().equals(packageNameList.getPackageNameList().get(i))) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                packageNameDataService.insertPackageName(secretKey, data.getPackageName());
            }
        }

        responseHeaders = new HttpHeaders();
        result.put("result", "Log Data Transfer Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/logdata/{query}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteLogData(@RequestHeader(value = "secretKey") String secretKey, @RequestParam(value = "packagename") String packageName) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();

        logDataService.delete(packageName);

        responseHeaders = new HttpHeaders();
        result.put("result", "Log Data Delete Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/logdata/filter/level/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<LogVO> logDataLevelList(@RequestHeader(value = "secretKey") String secretKey, @RequestParam(value = "packagename") String packageName, @RequestParam(value = "level") String level) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(secretKey);
        for (int i = 0; i < packageNameVO.getPackageNameList().size(); i++) {
            if (packageName.equals(packageNameVO.getPackageNameList().get(i))) {
                List<LogVO> logVOList = this.logDataService.findByPackageNameAndLevel(packageName, level, new Sort(Sort.Direction.DESC, "time"));
                return logVOList;
            }
        }
        return null;
    }

    @RequestMapping(value = "/logdata/filter/tag/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<LogVO> logDataTagList(@RequestHeader(value = "secretKey") String secretKey, @RequestParam(value = "packagename") String packageName, @RequestParam(value = "tag") String tag) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(secretKey);
        for (int i = 0; i < packageNameVO.getPackageNameList().size(); i++) {
            if (packageName.equals(packageNameVO.getPackageNameList().get(i))) {
                List<LogVO> logVOList = this.logDataService.findByPackageNameAndTag(packageName, tag, new Sort(Sort.Direction.DESC, "time"));
                return logVOList;
            }
        }
        return null;
    }

    @RequestMapping(value = "/logdata/tag/set/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    private Set<String> getTag(@RequestHeader(value = "secretKey") String secretKey,  @RequestParam(value="packageName") String packageName) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(secretKey);

        for (int i = 0; i < packageNameVO.getPackageNameList().size(); i++) {
            if (packageNameVO.getPackageNameList().get(i).equals(packageName)) {
                List<LogVO> logList = this.logDataService.findByPackageName(packageName);
                Set<String> tagSet = new HashSet<String>();
                for (LogVO logData : logList) {
                    tagSet.add(logData.getTag());
                }
                return tagSet;
            }
        }

        return null;
    }

    @RequestMapping(value = "/logdata/packagename/set", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    private ArrayList<String> getPackageName(@RequestHeader(value = "secretKey") String secretKey) {
        PackageNameVO packageNameList = this.packageNameDataService.findPackageNameVOByApiKey(secretKey);

        return packageNameList.getPackageNameList();
    }

    @RequestMapping(value = "/logdata/filter/packagename/{packagename}")
    @ResponseBody
    public List<LogVO> logDataPackageNameList(@RequestHeader(value = "secretKey") String secretKey, @RequestParam(value = "packagename") String packageName) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(secretKey);
        if (packageNameVO == null) {
            return null;
        }
        List<LogVO> logVOList = this.logDataService.findByPackageName(packageName, new Sort(Sort.Direction.DESC, "time"));

        return logVOList;
    }

    @RequestMapping(value = "/logdata/list", method = RequestMethod.GET)
    @ResponseBody
    public List<LogVO> allLogData() {
        return this.logDataService.findAll();
    }
}
