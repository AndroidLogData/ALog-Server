package com.logdata.web.controller;

import com.logdata.common.model.LogDataInfoVO;
import com.logdata.common.model.UserVO;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

@Controller
public class HelpController {
    private final RestAPIUtility restAPIUtility;

    @Autowired
    public HelpController(RestAPIUtility restAPIUtility) {
        this.restAPIUtility = restAPIUtility;
    }

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "help";
    }

    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public String myPage(Principal user, Model model) {
        if (user == null) {
            return "login";
        }

        LinkedHashMap<String, Integer> crashList = restAPIUtility.getCrashList("/mypage", getUserApiKey(user.getName()));
        ArrayList<LogDataInfoVO> logDataInfoVO = new ArrayList<>(Arrays.asList(restAPIUtility.getLogDataInfo("/main", getUserApiKey(user.getName()))));

        model.addAttribute("crashList", crashList);
        model.addAttribute("logDataInfo", logDataInfoVO);
        model.addAttribute("apikey", getUserApiKey(user.getName()));

        return "mypage";
    }

    public String getUserApiKey(String name) {
        UserVO u = this.restAPIUtility.findUser("/find", name);
        return u.getApiKey();
    }
}
