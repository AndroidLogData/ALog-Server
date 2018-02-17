package com.logdata.backend.controller;

import com.logdata.backend.model.CrashVO;
import com.logdata.backend.model.UserVO;
import com.logdata.backend.repository.CrashDataRepository;
import com.logdata.backend.repository.UserDataRepository;
import com.logdata.backend.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class HelpController {
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private CrashDataRepository crashDataRepository;

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "help";
    }

    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public String myPage(Principal user, Model model) {
        if (user == null) {
            return "login";
        }

        String apiKey = this.userDataRepository.findByUserID(user.getName()).getApiKey();
        ArrayList<CrashVO> crashVOArrayList = this.crashDataRepository.findAllByApiKeyOrderByTimeDesc(getUserApiKey(user));
        ArrayList<String> crashList = new ArrayList<String>();

        for (int i = 0; i < crashVOArrayList.size(); i++) {
            crashList.add(Utility.findCrashName(crashVOArrayList.get(i).getLogcat()));
        }

        model.addAttribute("crashList", crashList);
        model.addAttribute("apikey", apiKey);

        return "mypage";
    }

    public String getUserApiKey(Principal user) {
        UserVO u = this.userDataRepository.findByUserID(user.getName());
        return u.getApiKey();
    }
}
