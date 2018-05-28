package com.logdata.web.controller;

import com.logdata.common.model.LogDataInfoVO;
import com.logdata.common.model.UserVO;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

@Controller
public class MainController {
    private final RestAPIUtility restAPIUtility;

    @Autowired
    public MainController(RestAPIUtility restAPIUtility) {
        this.restAPIUtility = restAPIUtility;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal user, Model model) {
        if (user == null) {
            return "index";
        } else {
            ArrayList<String> packageNameList = restAPIUtility.getLogDataInfoSet("/logdata/packagename/set", getUserApiKey(user.getName()));
            model.addAttribute("packageNameList", packageNameList);
            return "index";
        }
    }

    @RequestMapping(value = "/main/{query}", method = RequestMethod.GET)
    public String moveBoard(Principal user, @RequestParam(value = "packageName") String packageName, Model model) {
        if (user == null) {
            return "login";
        }

        ArrayList<String> packageNameList = restAPIUtility.getLogDataInfoSet("/logdata/packagename/set", getUserApiKey(user.getName()));
        model.addAttribute("packageNameList", packageNameList);
        model.addAttribute("packageName", packageName);
        return "board";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ArrayList<LogDataInfoVO> mainPageDataList(Principal user) {
        if (user == null) {
            return null;
        }

        ArrayList<LogDataInfoVO> logDataInfoVO = new ArrayList<>(Arrays.asList(restAPIUtility.getLogDataInfo("/main", getUserApiKey(user.getName()))));

        return logDataInfoVO;
    }

    public String getUserApiKey(String name) {
        UserVO u = this.restAPIUtility.findUser("/find", name);
        return u.getApiKey();
    }
}
