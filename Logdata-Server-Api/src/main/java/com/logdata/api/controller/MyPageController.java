package com.logdata.api.controller;

import com.logdata.api.sevice.CrashDataService;
import com.logdata.common.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
