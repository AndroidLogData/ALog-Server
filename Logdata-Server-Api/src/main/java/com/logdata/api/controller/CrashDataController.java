package com.logdata.api.controller;

import com.logdata.api.sevice.CrashDataService;
import com.logdata.api.sevice.PackageNameDataService;
import com.logdata.common.model.CrashTimeVO;
import com.logdata.common.model.CrashVO;
import com.logdata.common.model.PackageNameVO;
import com.logdata.common.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/api")
public class CrashDataController {
    private final CrashDataService crashDataService;
    private final PackageNameDataService packageNameDataService;
    private final Utility utility;

    @Autowired
    public CrashDataController(CrashDataService crashDataService, PackageNameDataService packageNameDataService, Utility utility) {
        this.crashDataService = crashDataService;
        this.packageNameDataService = packageNameDataService;
        this.utility = utility;
    }

    @RequestMapping(value = "/crash/package-name/list", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> searchCrashVOOfPackageName(@RequestHeader(value = "apiKey") String apiKey) {
        return this.packageNameDataService.findPackageNameVOByApiKey(apiKey).getPackageNameList();
    }

    @RequestMapping(value = "/crash/filter/time/{query}", method = RequestMethod.GET)
    @ResponseBody
    public CrashVO crashDataTimeView(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "time") long time, @RequestParam(value = "package-name") String packageName) {
        PackageNameVO packageNameList = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);

        for (String item : packageNameList.getPackageNameList()) {
            if (item.equals(packageName)) {
                return this.crashDataService.findCrashVOByPackageNameAndTime(packageName, time);
            }
        }

        return null;
    }

    @RequestMapping(value = "/crash/filter/package-name/{query}", method = RequestMethod.GET)
    @ResponseBody
    public CrashVO crashPackageNamePage(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);

        for (String item : packageNameVO.getPackageNameList()) {
            if (item.equals(packageName)) {
                return this.crashDataService.findCrashVOByPackageNameOrderByTimeDesc(item);
            }
        }

        return null;
    }

    @RequestMapping(value = "/crash/filter/package-name/list/{query}", method = RequestMethod.GET)
    @ResponseBody
    public List<CrashVO> crashListOfPackageName(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        PackageNameVO packageNameList = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);

        for (String item : packageNameList.getPackageNameList()) {
            if (item.equals(packageName)) {
                return this.crashDataService.findByPackageNameOrderByTimeDesc(packageName);
            }
        }

        return null;
    }

    @RequestMapping(value = "/crash/package-name/time/{query}")
    @ResponseBody
    public List<CrashTimeVO> getCrashTime(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        PackageNameVO packageNameList = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);
        ArrayList<CrashTimeVO> crashTimeVOs = new ArrayList<CrashTimeVO>();

        for (String item : packageNameList.getPackageNameList()) {
            if (item.equals(packageName)) {
                List<CrashVO> crashList = this.crashDataService.findByPackageNameOrderByTimeAsc(item);

                for (CrashVO crashData : crashList) {
                    crashTimeVOs.add(new CrashTimeVO(crashData.getTime(), packageName, utility.getStringTimeToLong(crashData.getTime())));
                }

                return crashTimeVOs;
            }
        }

        return null;
    }

    @RequestMapping(value = "/crash", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> crashDataSave(@RequestHeader(value = "apiKey") String apiKey, @RequestBody CrashVO data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();

        if (apiKey.equals("")) {
            responseHeaders = new HttpHeaders();
            result.put("result", "Need API Key");

            return new ResponseEntity<>(result, responseHeaders, HttpStatus.FORBIDDEN);
        }

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Set<String> set = data.getDeviceFeatures().keySet();

        for (String s : set) {
            map.put(s.replace(".", "-"), data.getDeviceFeatures().get(s));
        }

        this.crashDataService.save(new CrashVO(
                data.getPackageName(),
                data.getTime(),
                data.getAndroidVersion(),
                data.getAppVersionCode(),
                data.getAppVersionName(),
                data.getAvailableMemorySize(),
                data.getBrand(),
                data.getLogcat(),
                data.getDeviceID(),
                data.getDisplay(),
                data.getEnvironment(),
                map,
                data.getBuild()));

        responseHeaders = new HttpHeaders();
        result.put("result", "Crash Data Transfer Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }
}
