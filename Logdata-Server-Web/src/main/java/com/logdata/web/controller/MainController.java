package com.logdata.web.controller;

import com.logdata.common.model.MainPageDataListResponse;
import com.logdata.common.model.MainPageVO;
import com.logdata.common.model.UserVO;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class MainController {
    private final RestAPIUtility restAPIUtility;

    @Autowired
    public MainController(RestAPIUtility restAPIUtility) {
        this.restAPIUtility = restAPIUtility;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public MainPageDataListResponse mainPageDataList(Principal user) {
        if (user == null) {
            return null;
        }

        MainPageVO[] mainPageVO = restAPIUtility.getMainData("/main", getUserApiKey(user.getName())).getBody();

        return new MainPageDataListResponse(new ArrayList<MainPageVO>(Arrays.asList(mainPageVO)));
    }

    public String getUserApiKey(String name) {
        UserVO u = this.restAPIUtility.findSecretKey("/find", name);
        return u.getApiKey();
    }
}
