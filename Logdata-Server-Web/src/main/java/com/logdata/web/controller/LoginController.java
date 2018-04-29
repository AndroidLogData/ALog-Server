package com.logdata.web.controller;

import com.logdata.common.model.UserRoles;
import com.logdata.common.model.UserVO;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Controller
public class LoginController {
    private final RestAPIUtility restAPIUtility;

    @Autowired
    public LoginController(RestAPIUtility restAPIUtility) {
        this.restAPIUtility = restAPIUtility;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String userLogin() {
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String userRegistration() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String userRegistration(@RequestParam Map<String, String> body) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (this.restAPIUtility.userRegistration("/registration", body.get("username"), passwordEncoder.encode(body.get("password")))) {
            return "login";
        } else {
            return "registration";
        }
    }
}
