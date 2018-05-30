package com.logdata.api.controller;

import com.logdata.api.sevice.CrashDataService;
import com.logdata.api.sevice.UserDataService;
import com.logdata.common.model.CrashVO;
import com.logdata.common.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/help")
public class MyPageController {
    private final CrashDataService crashDataService;
    private final Utility utility;

    @Autowired
    public MyPageController(CrashDataService crashDataService, Utility utility) {
        this.crashDataService = crashDataService;
        this.utility = utility;
    }

    @RequestMapping(value = "/my-page", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, Integer> myPage(@RequestHeader(value = "apiKey") String apiKey) {
        ArrayList<CrashVO> crashVOArrayList = this.crashDataService.findAllByApiKeyOrderByTimeDesc(apiKey);
        HashMap<String, Integer> crashList = new HashMap<String, Integer>();

        for (int i = 0; i < crashVOArrayList.size(); i++) {
            String packageName = utility.findCrashName(crashVOArrayList.get(i).getLogcat());

            if (crashList.get(packageName) == null) {
                crashList.put(packageName, 1);
            } else {
                int count = crashList.get(packageName);
                crashList.put(packageName, ++count);
            }
        }

        return crashList;
    }
}
