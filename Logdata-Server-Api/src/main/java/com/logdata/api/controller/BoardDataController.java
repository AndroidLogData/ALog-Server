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

import java.util.ArrayList;

@Controller
@RequestMapping("/board")
public class BoardDataController {
    private final LogDataService logDataService;
    private final CrashDataService crashDataService;
    private final PackageNameDataService packageNameDataService;
    private final Utility utility;

    @Autowired
    public BoardDataController(LogDataService logDataService, CrashDataService crashDataService, PackageNameDataService packageNameDataService, Utility utility) {
        this.logDataService = logDataService;
        this.crashDataService = crashDataService;
        this.packageNameDataService = packageNameDataService;
        this.utility = utility;
    }

    private ArrayList<String> getPackageNameList(String apiKey) {
        PackageNameVO packageNameList = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);

        return packageNameList.getPackageNameList();
    }

    @RequestMapping(value = "/detail/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataInfoVO mainPageDataList(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        LogDataInfoVO logDataInfo;

        int verbCount = this.logDataService.findByPackageNameAndLevel(packageName, "v").size();
        int infoCount = this.logDataService.findByPackageNameAndLevel(packageName, "i").size();
        int debugCount = this.logDataService.findByPackageNameAndLevel(packageName, "d").size();
        int warningCount = this.logDataService.findByPackageNameAndLevel(packageName, "w").size();
        int errorCount = this.logDataService.findByPackageNameAndLevel(packageName, "e").size();

        CrashVO crashTime = this.crashDataService.findByPackageNameAndApiKeyOrderByTimeDesc(packageName, apiKey);
        if (crashTime == null) {
            logDataInfo = new LogDataInfoVO(packageName, null, verbCount, infoCount, debugCount, warningCount, errorCount);
        } else {
            logDataInfo = new LogDataInfoVO(packageName, utility.getStringTimeToLong(crashTime.getTime()), verbCount, infoCount, debugCount, warningCount, errorCount);
        }

        return logDataInfo;
    }
}

