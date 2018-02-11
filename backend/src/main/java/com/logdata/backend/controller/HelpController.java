package com.logdata.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class HelpController {
    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "help";
    }

    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public String myPage() {
        return "mypage";
    }

    @RequestMapping(value="/session", method = RequestMethod.GET)
    String uid(HttpSession session, Model model, @RequestParam HashMap param) {
        System.out.println(session.getId());

        return "index";
    }
}
