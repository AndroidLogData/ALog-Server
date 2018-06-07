package com.logdata.api.controller;

import com.logdata.api.sevice.CrashDataService;
import com.logdata.api.sevice.LogDataService;
import com.logdata.common.model.CrashVO;
import com.logdata.common.model.LogDataInfoVO;
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
    private final Utility utility;

    @Autowired
    public BoardDataController(LogDataService logDataService, CrashDataService crashDataService, Utility utility) {
        this.logDataService = logDataService;
        this.crashDataService = crashDataService;
        this.utility = utility;
    }

    @RequestMapping(value = "/log-data/detail/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataInfoVO boardPageData(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        LogDataInfoVO logDataInfo;

        int verbCount = this.logDataService.findByPackageNameAndLevel(packageName, "v").size();
        int infoCount = this.logDataService.findByPackageNameAndLevel(packageName, "i").size();
        int debugCount = this.logDataService.findByPackageNameAndLevel(packageName, "d").size();
        int warningCount = this.logDataService.findByPackageNameAndLevel(packageName, "w").size();
        int errorCount = this.logDataService.findByPackageNameAndLevel(packageName, "e").size();

        CrashVO crashTime = this.crashDataService.findCrashVOByPackageNameOrderByTimeDesc(packageName);
        if (crashTime == null) {
            logDataInfo = new LogDataInfoVO(packageName, null, verbCount, infoCount, debugCount, warningCount, errorCount);
        } else {
            logDataInfo = new LogDataInfoVO(packageName, utility.getStringTimeToLong(crashTime.getTime()), verbCount, infoCount, debugCount, warningCount, errorCount);
        }

        return logDataInfo;
    }

    @RequestMapping(value = "/crash/detail/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public HashMap<String, Integer> boardPageCrashDataList(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        List<CrashVO> crashList = this.crashDataService.findByPackageNameOrderByTimeDesc(packageName);
        HashMap<String, Integer> result = new HashMap<String, Integer>();

        for (CrashVO aCrashList : crashList) {
            String crashName = utility.findCrashName(aCrashList.getLogcat());

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

