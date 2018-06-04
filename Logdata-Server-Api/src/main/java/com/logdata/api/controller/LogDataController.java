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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @RequestMapping(value = "/log-data", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> logDataSave(@RequestHeader(value = "apiKey") String apiKey, @RequestBody LogVO data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();

        if (apiKey.equals("")) {
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

        PackageNameVO packageNameList = packageNameDataService.findPackageNameVOByApiKey(apiKey);

        if (packageNameList.getPackageNameList().size() == 0) {
            packageNameDataService.insertPackageName(apiKey, data.getPackageName());
        } else {
            boolean flag = false;
            for (int i = 0; i < packageNameList.getPackageNameList().size(); i++) {
                if (data.getPackageName().equals(packageNameList.getPackageNameList().get(i))) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                packageNameDataService.insertPackageName(apiKey, data.getPackageName());
            }
        }

        responseHeaders = new HttpHeaders();
        result.put("result", "Log Data Transfer Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/log-data/{query}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteLogData(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();

        logDataService.delete(packageName);

        responseHeaders = new HttpHeaders();
        result.put("result", "Log Data Delete Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/log-data/filter/level/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<LogVO> logDataLevelList(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName, @RequestParam(value = "level") String level) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);
        for (int i = 0; i < packageNameVO.getPackageNameList().size(); i++) {
            if (packageName.equals(packageNameVO.getPackageNameList().get(i))) {
                List<LogVO> logVOList = this.logDataService.findByPackageNameAndLevel(packageName, level, new Sort(Sort.Direction.DESC, "time"));
                return logVOList;
            }
        }
        return null;
    }

    @RequestMapping(value = "/log-data/filter/tag/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<LogVO> logDataTagList(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName, @RequestParam(value = "tag") String tag) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);
        for (int i = 0; i < packageNameVO.getPackageNameList().size(); i++) {
            if (packageName.equals(packageNameVO.getPackageNameList().get(i))) {
                List<LogVO> logVOList = this.logDataService.findByPackageName(packageName, new Sort(Sort.Direction.DESC, "time"));
                List<LogVO> result = new ArrayList<>();

                for (LogVO item : logVOList) {
                    Pattern pattern = Pattern.compile("\\([0-9]*\\)");
                    Matcher matcher = pattern.matcher(item.getTag());
                    String str = matcher.replaceFirst("");
                    if (tag.equals(str)) {
                        result.add(item);
                    }
                }

                return result;
            }
        }
        return null;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/log-data/tag/set/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    private Set<String> getTag(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);

        for (int i = 0; i < packageNameVO.getPackageNameList().size(); i++) {
            if (packageNameVO.getPackageNameList().get(i).equals(packageName)) {
                List<LogVO> logList = this.logDataService.findByPackageName(packageName);
                Set<String> tagSet = new HashSet<String>();
                for (LogVO logData : logList) {
                    Pattern pattern = Pattern.compile("\\([0-9]*\\)");
                    Matcher matcher = pattern.matcher(logData.getTag());
                    String str = matcher.replaceFirst("");
                    tagSet.add(str);
                }
                return tagSet;
            }
        }

        return null;
    }

    @RequestMapping(value = "/log-data/package-name/set", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    private ArrayList<String> getPackageName(@RequestHeader(value = "apiKey") String apiKey) {
        PackageNameVO packageNameList = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);

        return packageNameList.getPackageNameList();
    }

    @RequestMapping(value = "/log-data/filter/package-name/{query}", method = RequestMethod.GET)
    @ResponseBody
    public List<LogVO> logDataPackageNameList(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);
        if (packageNameVO == null) {
            return null;
        }
        List<LogVO> logVOList = this.logDataService.findByPackageName(packageName, new Sort(Sort.Direction.DESC, "time"));

        return logVOList;
    }

    @RequestMapping(value = "/log-data/list", method = RequestMethod.GET)
    @ResponseBody
    public List<LogVO> allLogData() {
        return this.logDataService.findAll();
    }
}
