package com.logdata.api.controller;

import com.logdata.api.sevice.CrashDataService;
import com.logdata.api.sevice.LogDataService;
import com.logdata.common.model.CrashVO;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.LogDataInfoVO;
import com.logdata.common.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/main")
public class MainController {
    private final LogDataService logDataService;
    private final CrashDataService crashDataService;
    private final Utility utility;

    @Autowired
    public MainController(LogDataService logDataService, CrashDataService crashDataService, Utility utility) {
        this.logDataService = logDataService;
        this.crashDataService = crashDataService;
        this.utility = utility;
    }

    private Set<String> getPackageName(String apiKey) {
        List<LogVO> logData = this.logDataService.findByApiKey(apiKey, new Sort(Sort.Direction.DESC, "time"));

        Set<String> packageNameSet = new HashSet<String>();

        for (LogVO data : logData) {
            packageNameSet.add(data.getPackageName());
        }

        return packageNameSet;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<LogDataInfoVO> mainPageDataList(@RequestHeader(value = "secretKey") String secretKey) {
        Set<String> logData = getPackageName(secretKey);
        List<LogDataInfoVO> list = new ArrayList<LogDataInfoVO>();

        for (String packageName : logData) {
            int verbCount = this.logDataService.findByApiKeyAndPackageNameAndLevel(secretKey, packageName, "v").size();
            int infoCount = this.logDataService.findByApiKeyAndPackageNameAndLevel(secretKey, packageName, "i").size();
            int debugCount = this.logDataService.findByApiKeyAndPackageNameAndLevel(secretKey, packageName, "d").size();
            int warningCount = this.logDataService.findByApiKeyAndPackageNameAndLevel(secretKey, packageName, "w").size();
            int errorCount = this.logDataService.findByApiKeyAndPackageNameAndLevel(secretKey, packageName, "e").size();

            CrashVO crashTime = this.crashDataService.findByPackageNameAndApiKeyOrderByTimeDesc(packageName, secretKey);
            if (crashTime == null) {
                list.add(new LogDataInfoVO(packageName, null, verbCount, infoCount, debugCount, warningCount, errorCount));
            } else {
                list.add(new LogDataInfoVO(packageName, utility.getStringTimeToLong(crashTime.getTime()), verbCount, infoCount, debugCount, warningCount, errorCount));
            }
        }

        return list;
    }
}
