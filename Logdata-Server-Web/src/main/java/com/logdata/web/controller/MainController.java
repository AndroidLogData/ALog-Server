package com.logdata.web.controller;

import com.logdata.common.model.LogVO;
import com.logdata.common.model.MainPageDataListResponse;
import com.logdata.common.model.MainPageVO;
import com.logdata.common.model.UserVO;
import com.logdata.common.repository.CrashDataRepository;
import com.logdata.common.repository.LogDataRepository;
import com.logdata.common.repository.UserDataRepository;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private LogDataRepository logDataRepository;
    @Autowired
    private CrashDataRepository crashDataRepository;
    @Autowired
    private UserDataRepository userDataRepository;
    private final RestAPIUtility restAPIUtility;

    @Autowired
    public MainController(RestAPIUtility restAPIUtility) {
        this.restAPIUtility = restAPIUtility;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    private Set<String> getPackageName(String apiKey) {
        List<LogVO> logData = this.logDataRepository.findByApiKey(apiKey, new Sort(Sort.Direction.DESC, "time"));

        Set<String> packageNameSet = new HashSet<String>();

        for (LogVO data : logData) {
            packageNameSet.add(data.getPackageName());
        }

        return packageNameSet;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public MainPageDataListResponse mainPageDataList(Principal user) {
        if (user == null) {
            return null;
        }

        MainPageVO[] mainPageVO = restAPIUtility.getMainData("/main", getUserApiKey(user)).getBody();

        return new MainPageDataListResponse(new ArrayList<MainPageVO>(Arrays.asList(mainPageVO)));
    }

    public String getUserApiKey(Principal user) {
        UserVO u = this.userDataRepository.findByUserID(user.getName());
        return u.getApiKey();
    }
}
