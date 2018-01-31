package com.logdata.logcat.controller;

import com.logdata.logcat.model.UserRoles;
import com.logdata.logcat.model.UserVO;
import com.logdata.logcat.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

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
    public String UserRegistration(@RequestParam Map<String, String> body, Model model) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserVO newUser = new UserVO(body.get("username"), passwordEncoder.encode(body.get("password")));
        UserRoles userRoles = new UserRoles();
        userRoles.setRoleName("USER");
        newUser.setRoles(Collections.singletonList(userRoles));

        newUser.setApiKey(generatedApiKey());

        if (this.userDataRepository.findByUserID(body.get("username")) == null) {
            this.userDataRepository.save(newUser);
        } else {
            model.addAttribute("sameID", true);
            return "registration";
        }

        return "login";
    }

    private String generatedApiKey() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
