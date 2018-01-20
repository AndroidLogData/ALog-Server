package com.logdata.logcat.controller;

import com.logdata.logcat.model.LogData;
import com.logdata.logcat.repository.LogDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LogDataController {
    @Autowired
    private LogDataRepository repository;

    @RequestMapping(value = "/logdata", method = RequestMethod.GET)
    public String logDataView() {
        return "logDataView";
    }

    @RequestMapping(value = "/logdata", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> logDataSave(@RequestBody LogData data) {
        this.repository.save(new LogData(data.getPackageName(),
                data.getLevel(),
                data.getTag(),
                data.getMessage(),
                data.getTime(),
                data.getTotalMemory(),
                data.getAvailMemory(),
                data.getMemoryPercentage(),
                data.isLowMemory(),
                data.getThreshold(),
                data.getDalvikPss(),
                data.getNativePss(),
                data.getOtherPss(),
                data.getTotalPss()));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }
}
