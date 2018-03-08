package com.logdata.api.controller;

import com.logdata.api.sevice.UserDataService;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.UserVO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> userRegistration(@RequestBody UserVO data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();

        this.userDataService.save(new UserVO(
                data.getUserID(),
                data.getPassword()
                ));

        responseHeaders = new HttpHeaders();
        result.put("result", "Log Data Transfer Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }

//    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = "application/json")
//    @ResponseBody
//    public UserVO findByUserID(String name) {
//        return this.userDataService.findByUserID(name);
//    }
}
