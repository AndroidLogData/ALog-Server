package com.logdata.api.controller;

import com.logdata.api.sevice.UserDataService;
import com.logdata.common.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserDataController {
    private final UserDataService userDataService;

    @Autowired
    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @RequestMapping(value = "/find/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public UserVO findByApiKey(@RequestParam(value = "name") String name) {
        return this.userDataService.findByUserID(name);
    }

//    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = "application/json")
//    @ResponseBody
//    public UserVO findByUserID(String name) {
//        return this.userDataService.findByUserID(name);
//    }
}
