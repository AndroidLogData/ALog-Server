package com.logdata.logcat.controller;

import com.logdata.logcat.model.LogData;
import com.logdata.logcat.repository.LogDataRepository;
import com.logdata.logcat.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class LogDataController {
    @Autowired
    private LogDataRepository repository;

    @RequestMapping(value = "/logdata", method = RequestMethod.GET)
    public String logDataView(Model model) {
        List<LogData> logData = this.repository.findAll(new Sort(Sort.Direction.DESC, "time"));

        for (LogData data : logData) {
            data.setStringTime(Utility.getTime(data.getTime()));
        }

        model.addAttribute("items", logData);
        model.addAttribute("tagItems", getTag());
        model.addAttribute("packageNameItems", getPackageName());

        return "logdata";
    }

    @RequestMapping(value = "/logdatalevelfilter/{level}", method = RequestMethod.GET)
    public String logDataLevelView(@RequestParam(value = "level") String level, Model model) {
        List<LogData> logData = this.repository.findByLevel(level);

        model.addAttribute("items", logData);
        model.addAttribute("tagItems", getTag());
        model.addAttribute("packageNameItems", getPackageName());

        return "logdata";
    }

    @RequestMapping(value = "/logdatatagfilter/{tag}", method = RequestMethod.GET)
    public String logDataTagView(@RequestParam(value = "tag") String tag, Model model) {
        List<LogData> logData = this.repository.findByTag(tag);

        model.addAttribute("items", logData);
        model.addAttribute("tagItems", getTag());
        model.addAttribute("packageNameItems", getPackageName());

        return "logdata";
    }

    @RequestMapping(value = "/logdatapackagenamefilter/{packageName}", method = RequestMethod.GET)
    public String logDataPackageNameView(@RequestParam(value = "packageName") String packageName, Model model) {
        List<LogData> logData = this.repository.findByPackageName(packageName);

        model.addAttribute("items", logData);
        model.addAttribute("tagItems", getTag());
        model.addAttribute("packageNameItems", getPackageName());

        return "logdata";
    }

    private Set<String> getTag() {
        List<LogData> logData = this.repository.findAll();

        Set<String> tagSet = new HashSet<String>();

        for (LogData aLogData : logData) {
            tagSet.add(aLogData.getTag());
        }

        return tagSet;
    }

    private Set<String> getPackageName() {
        List<LogData> logData = this.repository.findAll();

        Set<String> packageNameSet = new HashSet<String>();

        for (LogData aLogData : logData) {
            packageNameSet.add(aLogData.getPackageName());
        }

        return packageNameSet;
    }

    @RequestMapping(value = "/logdata", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> logDataSave(@RequestBody LogData data) {
        this.repository.save(new LogData(data.getPackageName(),
                data.getLevel(),
                data.getTag(),
                data.getMessage(),
                data.getTime(),
                data.getTotalMemory(),
                data.getAvailMemory(),
                data.getMemoryPercentage(),
                data.isLowMemory(),
                data.getThreshold(),
                data.getDalvikPss(),
                data.getNativePss(),
                data.getOtherPss(),
                data.getTotalPss()));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }
}
