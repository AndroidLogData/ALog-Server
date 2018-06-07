package com.logdata.web.controller;

import com.logdata.common.model.LogDataInfoVO;
import com.logdata.common.model.UserVO;
import com.logdata.web.service.RestAPIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class MyPageController {
    private final RestAPIManager restAPIManager;

    @Autowired
    public MyPageController(RestAPIManager restAPIManager) {
        this.restAPIManager = restAPIManager;
    }

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "help";
    }

    @RequestMapping(value = "/my-page", method = RequestMethod.GET)
    public String myPage(Principal user, Model model) {
        if (user == null) {
            return "login";
        }

        LogDataInfoVO[] logDataInfoVO = restAPIManager.getLogDataInfo(getUserApiKey(user.getName()));

        model.addAttribute("logDataInfo", logDataInfoVO);
        model.addAttribute("apikey", getUserApiKey(user.getName()));
        model.addAttribute("packageNameList", getPackageName(user.getName()));

        return "mypage";
    }

    @RequestMapping(value = "/log-data/{query}", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String logDataDelete(Principal user, @RequestParam(value = "package-name") String packageName, Model model) {
        if (user == null) {
            return "login";
        }

        LogDataInfoVO[] logDataInfoVO = restAPIManager.getLogDataInfo(getUserApiKey(user.getName()));

        model.addAttribute("logDataInfo", logDataInfoVO);
        model.addAttribute("apikey", getUserApiKey(user.getName()));
        model.addAttribute("packageNameList", getPackageName(user.getName()));

        return "mypage";
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
