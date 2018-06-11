package com.logdata.web.controller;

import com.logdata.common.model.LogDataInfoVO;
import com.logdata.common.model.UserVO;
import com.logdata.web.service.RestAPIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class MainController {
    private final RestAPIManager restAPIManager;

    @Autowired
    public MainController(RestAPIManager restAPIManager) {
        this.restAPIManager = restAPIManager;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal user, Model model) {
        if (user == null) {
            return "index";
        } else {
            LogDataInfoVO[] logDataInfoList = restAPIManager.getLogDataInfo(getUserApiKey(user.getName()));

            model.addAttribute("logDataInfoList", logDataInfoList);
            model.addAttribute("packageNameList", getPackageName(user.getName()));

            return "index";
        }
    }

    private ArrayList<String> getPackageName(String name) {
        ArrayList<String> body = restAPIManager.getLogDataOfPackageName(getUserApiKey(name));

        return body;
    }

    public String getUserApiKey(String name) {
        UserVO u = this.restAPIManager.findUser(name);
        return u.getApiKey();
    }
}
