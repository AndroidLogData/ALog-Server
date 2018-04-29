package com.logdata.api.controller;

import com.logdata.api.sevice.UserDataService;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.UserRoles;
import com.logdata.common.model.UserVO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

        this.userDataService.save(new UserVO(
                body.get("username"),
                body.get("password"),
                Collections.singletonList(userRoles),
                generatedApiKey()
        ));

        responseHeaders = new HttpHeaders();
        result.put("result", true);
        result.put("message", "Registration Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }

    private String generatedApiKey() {
        String uuid = UUID.randomUUID().toString().replace("-", "");

        List<UserVO> user = this.userDataService.findAllByApiKey();

        for (int i = 0; i < user.size(); i++) {
            if (uuid.equals(user.get(i).getApiKey())) {
                return generatedApiKey();
            }
        }

        return uuid;
    }
}
