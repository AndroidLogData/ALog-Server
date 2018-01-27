package com.logdata.logcat.controller;

import com.logdata.logcat.model.UserRoles;
import com.logdata.logcat.model.UserVO;
import com.logdata.logcat.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserDataRepository userDataRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String UserLogin() {
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String UserRegistration() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String UserRegistration(@RequestParam Map<String, String> body) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserVO newUser = new UserVO(body.get("username"), passwordEncoder.encode(body.get("password")));
        UserRoles userRoles = new UserRoles();
        userRoles.setRoleName("USER");
        newUser.setRoles(Collections.singletonList(userRoles));

        this.userDataRepository.save(newUser);

        return "redirect:/login";
    }

//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public ResponseEntity<Map<String, String>> UserRegistration(@RequestParam Map<String, String> body) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        UserVO newUser = new UserVO(body.get("username"), passwordEncoder.encode(body.get("password")));
//        UserRoles userRoles = new UserRoles();
//        userRoles.setRoleName("USER");
//        newUser.setRoles(Collections.singletonList(userRoles));
//
//        this.userDataRepository.save(newUser);
//
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
//        Map<String, String> result = new HashMap<String, String>();
//        result.put("result", "User Registration Success");
//
//        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
//    }
}
