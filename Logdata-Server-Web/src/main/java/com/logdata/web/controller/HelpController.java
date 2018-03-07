package com.logdata.web.controller;

import com.logdata.common.model.CrashVO;
import com.logdata.common.model.UserVO;
import com.logdata.common.repository.CrashDataRepository;
import com.logdata.common.repository.UserDataRepository;
import com.logdata.common.util.Utility;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Controller
public class HelpController {
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private CrashDataRepository crashDataRepository;
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

        LinkedHashMap crashList = restAPIUtility.getCrashList("/mypage", getUserApiKey(user));

        model.addAttribute("crashList", crashList);
        model.addAttribute("apikey", getUserApiKey(user));

        return "mypage";
    }

    public String getUserApiKey(Principal user) {
        UserVO u = this.userDataRepository.findByUserID(user.getName());
        return u.getApiKey();
    }
}
