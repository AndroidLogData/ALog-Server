package com.logdata.api.controller;

import com.logdata.api.sevice.CrashDataService;
import com.logdata.api.sevice.LogDataService;
import com.logdata.api.sevice.PackageNameDataService;
import com.logdata.common.model.CrashVO;
import com.logdata.common.model.LogDataInfoVO;
import com.logdata.common.model.PackageNameVO;
import com.logdata.common.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardDataController {
    private final LogDataService logDataService;
    private final CrashDataService crashDataService;
    private final PackageNameDataService packageNameDataService;

    @Autowired
    public BoardDataController(LogDataService logDataService, CrashDataService crashDataService, PackageNameDataService packageNameDataService) {
        this.logDataService = logDataService;
        this.crashDataService = crashDataService;
        this.packageNameDataService = packageNameDataService;
    }

    @RequestMapping(value = "/log-data/detail/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataInfoVO boardPageData(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);

        for (String item : packageNameVO.getPackageNameList()) {
            if (item.equals(packageName)) {
                LogDataInfoVO logDataInfo;
                HashMap<String, Integer> logCount = new HashMap<String, Integer>();

                logCount.put("v", this.logDataService.findByPackageNameAndLevel(packageName, "v").size());
                logCount.put("i", this.logDataService.findByPackageNameAndLevel(packageName, "i").size());
                logCount.put("d", this.logDataService.findByPackageNameAndLevel(packageName, "d").size());
                logCount.put("w", this.logDataService.findByPackageNameAndLevel(packageName, "w").size());
                logCount.put("e", this.logDataService.findByPackageNameAndLevel(packageName, "e").size());

                CrashVO crashTime = this.crashDataService.findCrashVOByPackageNameOrderByTimeDesc(packageName);
                if (crashTime == null) {
                    logDataInfo = new LogDataInfoVO(packageName, null, logCount);
                } else {
                    logDataInfo = new LogDataInfoVO(packageName, Utility.getStringTimeToLong(crashTime.getTime()), logCount);
                }

                return logDataInfo;
            }
        }

        return null;
    }

    @RequestMapping(value = "/crash/detail/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public HashMap<String, Integer> boardPageCrashDataList(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);

        for (String item : packageNameVO.getPackageNameList()) {
            if (item.equals(packageName)) {
                List<CrashVO> crashList = this.crashDataService.findByPackageNameOrderByTimeDesc(item);
                HashMap<String, Integer> result = new HashMap<String, Integer>();

                for (CrashVO aCrashList : crashList) {
                    String crashName = Utility.findCrashName(aCrashList.getLogcat());

                    if (result.get(crashName) == null) {
                        result.put(crashName, 1);
                    } else {
                        int count = result.get(crashName);
                        result.put(crashName, ++count);
                    }
                }

                return result;
            }
        }
        return null;
    }
}

