package com.logdata.backend.service;

import com.logdata.backend.model.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    public CustomLogoutSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityUser userDetails = (SecurityUser) request.getSession().getAttribute("userLoginInfo");
        request.getSession().invalidate();

//        HttpSession session = request.getSession();
//        session.invalidate();
        System.out.println("Logout : " + request.getSession().getId());
    }
}
