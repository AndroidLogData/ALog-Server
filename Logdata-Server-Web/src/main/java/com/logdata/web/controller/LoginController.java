package com.logdata.web.controller;

import com.logdata.web.service.RestAPIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class LoginController {
    private final RestAPIManager restAPIManager;

    @Autowired
    public LoginController(RestAPIManager restAPIManager) {
        this.restAPIManager = restAPIManager;
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

        if (this.restAPIManager.userRegistration("/registration", body.get("username"), passwordEncoder.encode(body.get("password")))) {
            return "login";
        } else {
            return "registration";
        }
    }
}
