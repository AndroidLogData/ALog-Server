package com.logdata.web.controller;

import com.logdata.common.model.LogDataListResponse;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.SetDataListResponse;
import com.logdata.common.model.UserVO;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

@Controller
public class LogDataController {
    private final RestAPIUtility restAPIUtility;

    @Autowired
    public LogDataController(RestAPIUtility restAPIUtility) {
        this.restAPIUtility = restAPIUtility;
    }

    @RequestMapping(value = "/logdata", method = RequestMethod.GET)
    public String logDataView() {
        return "logdata";
    }

    @RequestMapping(value = "/logdata", method = RequestMethod.POST)
    public ResponseEntity<Object> logDataSave(@RequestHeader(value = "secretKey") String secretKey, @RequestBody LogVO data) {
        return restAPIUtility.postData("/api", "/logdata", secretKey, data);
    }

    @RequestMapping(value = "/logdata/filter/level/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataListResponse logDataLevelList(Principal user, @RequestParam(value = "packagename") String packageName, @RequestParam(value = "level") String level) {
        LogVO[] body = restAPIUtility.getLogDataLevel("/logdata/filter/level", getUserApiKey(user.getName()), packageName, level).getBody();

        ArrayList<LogVO> list = new ArrayList<LogVO>(Arrays.asList(body));

        return new LogDataListResponse(list);
    }

    @RequestMapping(value = "/logdata/filter/tag/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataListResponse logDataTagList(Principal user, @RequestParam(value = "packagename") String packageName, @RequestParam(value = "tag") String tag) {
        LogVO[] body = restAPIUtility.getLogDataTag("/logdata/filter/tag", getUserApiKey(user.getName()), packageName, tag).getBody();

        ArrayList<LogVO> list = new ArrayList<LogVO>(Arrays.asList(body));

        return new LogDataListResponse(list);
    }

    @RequestMapping(value = "/logdata/filter/packageName/{packagename}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataListResponse logDataPackageNameList(Principal user, @RequestParam(value = "packagename") String packageName) {
        LogVO[] body = restAPIUtility.getPackageNameList("/logdata/filter/packagename", getUserApiKey(user.getName()), packageName).getBody();

        ArrayList<LogVO> list = new ArrayList<LogVO>(Arrays.asList(body));

        return new LogDataListResponse(list);
    }

    @RequestMapping(value = "/logdata/packagename/set", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    private SetDataListResponse getPackageName(Principal user) {
        Set<String> body = restAPIUtility.getLogDataInfoSet("/logdata/packagename/set", getUserApiKey(user.getName())).getBody();

        return new SetDataListResponse(body);
    }

    @RequestMapping(value = "/logdata/tag/set", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    private SetDataListResponse getTagName(Principal user) {
        Set<String> body = restAPIUtility.getLogDataInfoSet("/logdata/tag/set", getUserApiKey(user.getName())).getBody();

        return new SetDataListResponse(body);
    }

    public String getUserApiKey(String name) {
        UserVO u = this.restAPIUtility.findSecretKey("/find", name);
        return u.getApiKey();
    }

    @RequestMapping(value = "/logdata/filter/packagename/{packageName}", method = RequestMethod.GET)
    public String logDataPackageNameView(Principal user) {
        if (user == null) {
            return "login";
        }
        return "index";
    }
}
