package com.logdata.web.controller;

import com.logdata.common.model.LogDataInfoVO;
import com.logdata.common.model.UserVO;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class BoardDataController {
    private final RestAPIUtility restAPIUtility;

    @Autowired
    public BoardDataController(RestAPIUtility restAPIUtility) {
        this.restAPIUtility = restAPIUtility;
    }

    @RequestMapping(value = "/board/{query}", method = RequestMethod.GET, produces = "application/json")
    public String logDataInfoOfPackageName(Principal user, @RequestParam(value = "package-name") String packageName, Model model, HttpSession session) {
        if (user == null) {
            return null;
        }

        session.setAttribute("packageName", packageName);

        LogDataInfoVO logDataInfoVO = restAPIUtility.getLogDataInfoOfPackageName("/detail", getUserApiKey(user.getName()), packageName);
        ArrayList<String> packageNameList = restAPIUtility.getLogDataInfoSet("/log-data/package-name/set", getUserApiKey(user.getName()));

        model.addAttribute("logDataInfoOfPackageName", logDataInfoVO);
        model.addAttribute("packageNameList", packageNameList);
        model.addAttribute("packageName", packageName);

        return "board";
    }

    public String getUserApiKey(String name) {
        UserVO u = this.restAPIUtility.findUser("/find", name);
        return u.getApiKey();
    }
}
