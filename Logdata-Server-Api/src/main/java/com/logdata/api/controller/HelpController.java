//package com.logdata.api.controller;
//
//import com.logdata.api.service.CrashDataService;
//import com.logdata.api.service.UserService;
//import com.logdata.common.model.CrashVO;
//import com.logdata.common.model.UserVO;
//import com.logdata.common.util.Utility;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//@Controller
//public class HelpController {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private CrashDataService crashDataService;
//
//    @RequestMapping(value = "/help", method = RequestMethod.GET)
//    public String help() {
//        return "help";
//    }
//
//    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
//    public String myPage(Principal user, Model model) {
//        if (user == null) {
//            return "login";
//        }
//
//        String apiKey = this.userService.findByUserID(user.getName()).getApiKey();
//        ArrayList<CrashVO> crashVOArrayList = this.crashDataService.findAllByApiKeyOrderByTimeDesc(getUserApiKey(user));
//        HashMap<String, Integer> crashList = new HashMap<String, Integer>();
//
//        for (int i = 0; i < crashVOArrayList.size(); i++) {
//            String packageName = Utility.findCrashName(crashVOArrayList.get(i).getLogcat());
//
//            if (crashList.get(packageName) == null) {
//                crashList.put(packageName, 1);
//            } else {
//                int count = crashList.get(packageName);
//                crashList.put(packageName, ++count);
//            }
//        }
//
//        model.addAttribute("crashList", crashList);
//        model.addAttribute("apikey", apiKey);
//
//        return "mypage";
//    }
//
//    public String getUserApiKey(Principal user) {
//        UserVO u = this.userService.findByUserID(user.getName());
//        return u.getApiKey();
//    }
//}
