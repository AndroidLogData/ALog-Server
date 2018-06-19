package com.logdata.api.controller;

import com.logdata.api.sevice.PackageNameDataService;
import com.logdata.api.sevice.UserDataService;
import com.logdata.common.model.UserRoles;
import com.logdata.common.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserDataController {
    private final UserDataService userDataService;
    private final PackageNameDataService packageNameDataService;

    @Autowired
    public UserDataController(UserDataService userDataService, PackageNameDataService packageNameDataService) {
        this.userDataService = userDataService;
        this.packageNameDataService = packageNameDataService;
    }

    @RequestMapping(value = "/find/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public UserVO findByApiKey(@RequestParam(value = "name") String name) {
        return this.userDataService.findByUserID(name);
    }

    @RequestMapping(value = "/registration/{query}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> userRegistration(@RequestParam Map<String, String> body) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<String, Object>();

        UserRoles userRoles = new UserRoles();
        userRoles.setRoleName("USER");

        UserVO user = this.userDataService.findByUserID(body.get("username"));

        if (user != null) {
            responseHeaders = new HttpHeaders();
            result.put("result", false);
            result.put("message", "already user");

            return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
        }

        String apiKey = generatedApiKey();

        this.userDataService.save(new UserVO(
                body.get("username"),
                body.get("password"),
                Collections.singletonList(userRoles),
                apiKey
        ));

        packageNameDataService.insertUserApiKey(apiKey);

        responseHeaders = new HttpHeaders();
        result.put("result", true);
        result.put("message", "Registration Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }

    private String generatedApiKey() {
        String uuid = UUID.randomUUID().toString().replace("-", "");

        List<UserVO> user = this.userDataService.findAll();

        for (UserVO anUser : user) {
            if (uuid.equals(anUser.getApiKey())) {
                return generatedApiKey();
            }
        }

        return uuid;
    }
}
