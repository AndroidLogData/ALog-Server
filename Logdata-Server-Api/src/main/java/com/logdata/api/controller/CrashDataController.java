package com.logdata.api.controller;

import com.logdata.api.sevice.CrashDataService;
import com.logdata.common.model.CrashTimeVO;
import com.logdata.common.model.CrashVO;
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
    private final Utility utility;

    @Autowired
    public CrashDataController(CrashDataService crashDataService, Utility utility) {
        this.crashDataService = crashDataService;
        this.utility = utility;
    }

    @RequestMapping(value = "/crash/filter/packageName", method = RequestMethod.GET)
    @ResponseBody
    public Set<String> searchCrashVOOfPackageName(@RequestHeader(value = "apiKey") String apiKey) {
        List<CrashVO> crashList = this.crashDataService.findByApiKey(apiKey);
        Set<String> packageNameList = new HashSet<String>();

        for (CrashVO crashData : crashList) {
            packageNameList.add(crashData.getPackageName());
        }

        return packageNameList;
    }

    @RequestMapping(value = "/crash/filter/time/{time}", method = RequestMethod.GET)
    @ResponseBody
    public CrashVO crashDataTimeView(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "time") long time, @RequestParam(value = "packageName") String packageName) {
        CrashVO crashVO = this.crashDataService.findCrashDataByTimeAndApiKeyAndPackageName(time, apiKey, packageName);

        if (crashVO == null || !(apiKey.equals(crashVO.getApiKey()))) {
            return null;
        }

        return crashVO;
    }

    @RequestMapping(value = "/crash/filter/packagename/{packageName}", method = RequestMethod.GET)
    @ResponseBody
    public CrashVO crashPackageNamePage(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "packageName") String packageName) {
        CrashVO crashVO = this.crashDataService.findCrashDataByPackageNameAndApiKeyOrderByTimeDesc(packageName, apiKey);

        if (crashVO == null) {
            return null;
        }

        return crashVO;
    }

    @RequestMapping(value = "/crash/packagename/set")
    @ResponseBody
    public Set<String> getPackageName(@RequestHeader(value = "apiKey") String apiKey) {
        List<CrashVO> setData = this.crashDataService.findByApiKey(apiKey);

        Set<String> packageNameSet = new HashSet<String>();

        for (CrashVO data : setData) {
            packageNameSet.add(data.getPackageName());
        }

        return packageNameSet;
    }

    @RequestMapping(value = "/crash/packagename/time/{packageName}")
    @ResponseBody
    public List<CrashTimeVO> getCrashTime(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "packageName") String packageName) {
        ArrayList<CrashVO> list = this.crashDataService.findByApiKeyAndPackageNameOrderByTimeAsc(apiKey, packageName);
        ArrayList<CrashTimeVO> crashTimeVOs = new ArrayList<CrashTimeVO>();

        for (int i = 0; i < list.size(); i++) {
            crashTimeVOs.add(new CrashTimeVO(list.get(i).getTime(), packageName, utility.getStringTimeToLong(list.get(i).getTime())));
        }

        return crashTimeVOs;
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
                data.getBuild(),
                apiKey));

        responseHeaders = new HttpHeaders();
        result.put("result", "Crash Data Transfer Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }
}
