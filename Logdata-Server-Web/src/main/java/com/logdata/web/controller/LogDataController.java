package com.logdata.web.controller;

import com.logdata.common.model.LogVO;
import com.logdata.common.model.UserVO;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @RequestMapping(value = "/log-data", method = RequestMethod.GET)
    public String logDataView() {
        return "logdata";
    }

    @RequestMapping(value = "/log-data", method = RequestMethod.POST)
    public ResponseEntity<Object> logDataSave(@RequestHeader(value = "apiKey") String apiKey, @RequestBody LogVO data) {
        return restAPIUtility.postData("/api", "/log-data", apiKey, data);
    }

    @RequestMapping(value = "/log-data/filter/level/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ArrayList<LogVO> logDataLevelList(Principal user, @RequestParam(value = "package-name") String packageName, @RequestParam(value = "level") String level) {
        if (user == null) {
            return null;
        }

        LogVO[] body = restAPIUtility.getLogDataLevel("/log-data/filter/level", getUserApiKey(user.getName()), packageName, level);

        ArrayList<LogVO> list = new ArrayList<LogVO>(Arrays.asList(body));

        return list;
    }

    @RequestMapping(value = "/log-data/filter/tag/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ArrayList<LogVO> logDataTagList(Principal user, @RequestParam(value = "package-name") String packageName, @RequestParam(value = "tag") String tag) {
        if (user == null) {
            return null;
        }

        LogVO[] body = restAPIUtility.getLogDataTag("/log-data/filter/tag", getUserApiKey(user.getName()), packageName, tag);

        ArrayList<LogVO> list = new ArrayList<LogVO>(Arrays.asList(body));

        return list;
    }

    @RequestMapping(value = "/log-data/filter/package-name/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ArrayList<LogVO> logDataPackageNameList(Principal user, @RequestParam(value = "package-name") String packageName) {
        if (user == null) {
            return null;
        }

        LogVO[] body = restAPIUtility.searchLogDataOfPackageName("/log-data/filter/package-name", getUserApiKey(user.getName()), packageName);

        ArrayList<LogVO> list = new ArrayList<LogVO>(Arrays.asList(body));

        return list;
    }

    @RequestMapping(value = "/log-data/package-name/set", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    private ArrayList<String> getPackageName(Principal user) {
        if (user == null) {
            return null;
        }

        ArrayList<String> body = restAPIUtility.getLogDataInfoSet("/log-data/package-name/set", getUserApiKey(user.getName()));

        return body;
    }

    @RequestMapping(value = "/log-data/tag/set/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    private Set<String> getTagName(Principal user, @RequestParam(value = "package-name") String packageName) {
        if (user == null) {
            return null;
        }

        Set<String> body = restAPIUtility.getLogDataInfoSet("/log-data/tag/set", getUserApiKey(user.getName()), packageName);

        return body;
    }

    public String getUserApiKey(String name) {
        UserVO u = this.restAPIUtility.findUser("/find", name);
        return u.getApiKey();
    }

    @RequestMapping(value = "/log-data/filter/package-name/{query}", method = RequestMethod.GET)
    public String logDataPackageNameView(Principal user, Model model, HttpSession session) {
        if (user == null) {
            return "login";
        }

        model.addAttribute("packageName", session.getAttribute("packageName"));

        return "logdata";
    }
}
