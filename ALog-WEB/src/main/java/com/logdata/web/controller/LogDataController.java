package com.logdata.web.controller;

import com.logdata.common.model.LogVO;
import com.logdata.common.model.UserVO;
import com.logdata.web.service.RestAPIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class LogDataController {
    private final RestAPIManager restAPIManager;

    @Autowired
    public LogDataController(RestAPIManager restAPIManager) {
        this.restAPIManager = restAPIManager;
    }

    @RequestMapping(value = "/log-data", method = RequestMethod.GET)
    public String logDataView() {
        return "logdata";
    }

    @RequestMapping(value = "/log-data", method = RequestMethod.POST)
    public ResponseEntity<Object> logDataSave(@RequestHeader(value = "apiKey") String apiKey, @RequestBody LogVO data) {
        return restAPIManager.sendLogData(apiKey, data);
    }

    private ArrayList<String> getPackageName(String name) {
        return restAPIManager.getLogDataOfPackageName(getUserApiKey(name));
    }

    public String getUserApiKey(String name) {
        UserVO u = this.restAPIManager.findUser(name);
        return u.getApiKey();
    }

    @RequestMapping(value = "/log-data/filter/**", method = RequestMethod.GET)
    public String logDataPackageNameView(Principal user, Model model, HttpSession session) {
        if (user == null) {
            return "login";
        }

        model.addAttribute("packageName", session.getAttribute("packageName"));
        model.addAttribute("packageNameList", getPackageName(user.getName()));

        return "logdata";
    }
}
