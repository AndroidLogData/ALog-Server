package com.logdata.web.controller;

import com.logdata.common.model.LogDataListResponse;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.SetDataListResponse;
import com.logdata.common.model.UserVO;
import com.logdata.common.repository.UserDataRepository;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class LogDataController {
    @Autowired
    private UserDataRepository userDataRepository;

    @RequestMapping(value = "/logdata", method = RequestMethod.GET)
    public String logDataView() {
        return "logdata";
    }

    @RequestMapping(value = "/logdata", method = RequestMethod.POST)
    public ResponseEntity<Object> logDataSave(@RequestHeader(value = "secretKey") String secretKey, @RequestBody LogVO data) {
        return RestAPIUtility.postData("/logdatasave", secretKey, data);
    }

    @RequestMapping(value = "/logdatalevelfilter/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataListResponse logDataLevelList(Principal user, @RequestParam(value = "packagename") String packageName, @RequestParam(value = "level") String level) {
        return RestAPIUtility.getLogDataLevel("/logdatalevelfilter", getUserApiKey(user), packageName, level);
    }

    @RequestMapping(value = "/logdatatagfilter/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataListResponse logDataTagList(Principal user, @RequestParam(value = "packagename") String packageName, @RequestParam(value = "tag") String tag) {
        return RestAPIUtility.getLogDataTag("/logdatatagfilter", getUserApiKey(user), packageName, tag);
    }

    @RequestMapping(value = "/logdatapackagenamefilter/{packagename}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public LogDataListResponse logDataPackageNameList(Principal user, @RequestParam(value = "packagename") String packageName) {
        return RestAPIUtility.getPackageNameList("/logdatapackagenamefilter", getUserApiKey(user), packageName);
    }

    @RequestMapping(value = "/packagenamedatalist", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    private SetDataListResponse getPackageName(Principal user) {
        return RestAPIUtility.getSetListData("/packagenamedatalist", getUserApiKey(user));
    }

    @RequestMapping(value = "/tagdatalist", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    private SetDataListResponse getTagName(Principal user) {
        return RestAPIUtility.getSetListData("/tagdatalist", getUserApiKey(user));
    }

    public String getUserApiKey(Principal user) {
        UserVO u = this.userDataRepository.findByUserID(user.getName());
        return u.getApiKey();
    }

    @RequestMapping(value = "/packagenamefilter/{packageName}", method = RequestMethod.GET)
    public String logDataPackageNameView(Principal user) {
        if (user == null) {
            return "login";
        }
        return "index";
    }
}
