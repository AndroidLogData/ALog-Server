package com.logdata.web.controller;

import com.logdata.common.model.UserVO;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class BoardDataController {
    private final RestAPIUtility restAPIUtility;

    @Autowired
    public BoardDataController(RestAPIUtility restAPIUtility) {
        this.restAPIUtility = restAPIUtility;
    }

//    @RequestMapping(value = "/main/{query}", method = RequestMethod.GET)
//    public String moveBoard(Principal user, @RequestParam(value = "packageName") String packageName, Model model) {
//        if (user == null) {
//            return "login";
//        }
//
//        ArrayList<String> packageNameList = restAPIUtility.getLogDataInfoSet("/logdata/packagename/set", getUserApiKey(user.getName()));
//        model.addAttribute("packageNameList", packageNameList);
//        model.addAttribute("packageName", packageName);
//        return "board";
//    }

    public String getUserApiKey(String name) {
        UserVO u = this.restAPIUtility.findUser("/find", name);
        return u.getApiKey();
    }
}
