package com.logdata.logcat.controller;

import com.logdata.logcat.model.LogVO;
import com.logdata.logcat.model.LogDataListResponse;
import com.logdata.logcat.model.UserVO;
import com.logdata.logcat.repository.LogDataRepository;
import com.logdata.logcat.repository.UserDataRepository;
import com.logdata.logcat.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
public class LogDataController {
    @Autowired
    private LogDataRepository repository;
    @Autowired
    private UserDataRepository userDataRepository;

    @RequestMapping(value = "/logdata", method = RequestMethod.GET)
    public String logDataView(Principal user, Model model) {
        List<LogVO> logData = this.repository.findByApiKey(getUserApiKey(user), new Sort(Sort.Direction.DESC, "time"));

        model.addAttribute("items", logData);
        model.addAttribute("tagItems", getTag());
        model.addAttribute("packageNameItems", getPackageName());

        return "logdata";
    }

    @RequestMapping(value = "/logdatalevelfilter/{level}", method = RequestMethod.GET)
    public String logDataLevelView(Principal user, @RequestParam(value = "level") String level, Model model) {
        List<LogVO> logData = this.repository.findByLevel(getUserApiKey(user), level, new Sort(Sort.Direction.DESC, "time"));

        model.addAttribute("items", logData);
        model.addAttribute("tagItems", getTag());
        model.addAttribute("packageNameItems", getPackageName());

        return "logdata";
    }

    @RequestMapping(value = "/logdatatagfilter/{tag}", method = RequestMethod.GET)
    public String logDataTagView(Principal user, @RequestParam(value = "tag") String tag, Model model) {
        List<LogVO> logData = this.repository.findByTag(getUserApiKey(user), tag, new Sort(Sort.Direction.DESC, "time"));

        model.addAttribute("items", logData);
        model.addAttribute("tagItems", getTag());
        model.addAttribute("packageNameItems", getPackageName());

        return "logdata";
    }

    @RequestMapping(value = "/logdatapackagenamefilter/{packageName}", method = RequestMethod.GET)
    public String logDataPackageNameView(Principal user, @RequestParam(value = "packageName") String packageName, Model model) {
        List<LogVO> logData = this.repository.findByPackageName(getUserApiKey(user), packageName, new Sort(Sort.Direction.DESC, "time"));

        model.addAttribute("items", logData);
        model.addAttribute("tagItems", getTag());
        model.addAttribute("packageNameItems", getPackageName());

        return "logdata";
    }

    private Set<String> getTag() {
        List<LogVO> logData = this.repository.findAll();

        Set<String> tagSet = new HashSet<String>();

        for (LogVO data : logData) {
            tagSet.add(data.getTag());
        }

        return tagSet;
    }

    private Set<String> getPackageName() {
        List<LogVO> logData = this.repository.findAll();

        Set<String> packageNameSet = new HashSet<String>();

        for (LogVO data : logData) {
            packageNameSet.add(data.getPackageName());
        }

        return packageNameSet;
    }

    @RequestMapping(value = "/logdata", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> logDataSave(@RequestHeader(value = "secretKey") String secretKey, @RequestBody LogVO data) {
        if (Utility.CheckedSecretKey(secretKey)) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
            Map<String, String> result = new HashMap<String, String>();
            result.put("result", "Need API Key");

            return new ResponseEntity<>(result, responseHeaders, HttpStatus.BAD_REQUEST);
        }

        this.repository.save(new LogVO(data.getPackageName(),
                data.getLevel(),
                data.getTag(),
                data.getMessage(),
                data.getTime(),
                data.getMemoryInfo(),
                secretKey));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "Log Data Transfer Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/logdatalist", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataListResponse list() {
        List<LogVO> logVOList = this.repository.findAll(new Sort(Sort.Direction.DESC, "time"));

        for (LogVO data : logVOList) {
            data.setStringTime(Utility.getTime(data.getTime()));
        }
        return new LogDataListResponse(logVOList);
    }

    public String getUserApiKey(Principal user) {
        UserVO u = this.userDataRepository.findByUserID(user.getName());
        return u.getApiKey();
    }
}
