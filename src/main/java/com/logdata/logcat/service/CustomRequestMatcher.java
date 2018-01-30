package com.logdata.logcat.service;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class CustomRequestMatcher implements RequestMatcher {
    private Pattern allowedMethods = Pattern.compile("^(GET|POST|HEAD|TRACE|OPTIONS)$");
    private RegexRequestMatcher unprotectedMatcher = new RegexRequestMatcher("/*", null);

    @Override
    public boolean matches(HttpServletRequest request) {
        if (allowedMethods.matcher(request.getMethod()).matches()) {
            return false;
        }
        return !unprotectedMatcher.matches(request);
    }
}
