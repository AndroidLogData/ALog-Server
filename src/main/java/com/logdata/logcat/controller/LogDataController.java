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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        if (user == null) {
            return "nodata";
        }
        UserVO u = this.userDataRepository.findByUserID(user.getName());
        List<LogVO> logData = this.repository.findByApiKey(u.getApiKey(), new Sort(Sort.Direction.DESC, "time"));
        System.out.println(user.getName());
//        List<LogVO> logData = this.repository.findAll(new Sort(Sort.Direction.DESC, "time"));

        if (Utility.isNoData(logData)) {
            return "nodata";
        }

        model.addAttribute("items", logData);
        model.addAttribute("tagItems", getTag());
        model.addAttribute("packageNameItems", getPackageName());

        return "logdata";
    }

    @RequestMapping(value = "/logdatalevelfilter/{level}", method = RequestMethod.GET)
    public String logDataLevelView(@RequestParam(value = "level") String level, Model model) {
        List<LogVO> logData = this.repository.findByLevel(level, new Sort(Sort.Direction.DESC, "time"));

        model.addAttribute("items", logData);
        model.addAttribute("tagItems", getTag());
        model.addAttribute("packageNameItems", getPackageName());

        return "logdata";
    }

    @RequestMapping(value = "/logdatatagfilter/{tag}", method = RequestMethod.GET)
    public String logDataTagView(@RequestParam(value = "tag") String tag, Model model) {
        List<LogVO> logData = this.repository.findByTag(tag, new Sort(Sort.Direction.DESC, "time"));

        model.addAttribute("items", logData);
        model.addAttribute("tagItems", getTag());
        model.addAttribute("packageNameItems", getPackageName());

        return "logdata";
    }

    @RequestMapping(value = "/logdatapackagenamefilter/{packageName}", method = RequestMethod.GET)
    public String logDataPackageNameView(@RequestParam(value = "packageName") String packageName, Model model) {
        List<LogVO> logData = this.repository.findByPackageName(packageName, new Sort(Sort.Direction.DESC, "time"));

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
        if (secretKey.equals("")) {
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
                data.getTotalMemory(),
                data.getAvailMemory(),
                data.getMemoryPercentage(),
                data.isLowMemory(),
                data.getThreshold(),
                data.getDalvikPss(),
                data.getNativePss(),
                data.getOtherPss(),
                data.getTotalPss(),
                secretKey));

        System.out.println(secretKey);

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
}
