package com.logdata.logcat.controller;

import com.logdata.logcat.model.ChartData;
import com.logdata.logcat.model.CrashData;
import com.logdata.logcat.repository.CrashDataRepository;
import com.logdata.logcat.util.Utility;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class CrashController {
    @Autowired
    private CrashDataRepository repository;

    @RequestMapping(value = "/crash", method = RequestMethod.GET)
    public String crashPage(Model model) {
        CrashData crashData = this.repository.findCrashDataBy(new Sort(Sort.Direction.DESC, "time"));
        List<CrashData> chartTimeData = this.repository.findAll(new Sort(Sort.Direction.ASC, "time"));

        if (crashData == null) {
            return "nodata";
        }

        Object display = crashData.getDisplay().get("0");

        Map<String, Object> deviceFeatures = new LinkedHashMap<String, Object>();
        Set<String> deviceFeaturesKey = crashData.getDeviceFeatures().keySet();

        for (String s : deviceFeaturesKey) {
            deviceFeatures.put(s.replace("-", "."), crashData.getDeviceFeatures().get(s));
        }

        LinkedHashSet<ChartData> chartData = new LinkedHashSet<ChartData>();
        for (CrashData data : chartTimeData) {
            chartData.add(new ChartData(Utility.getChartDataDate(data.getTime()), Utility.getChartDataTime(data.getTime())));
        }

        model.addAttribute("crash", crashData);
        model.addAttribute("chartData", chartData);
        model.addAttribute("time", crashData.getTime());
        model.addAttribute("realSize", ((LinkedHashMap<String, Object>) display).get("realSize"));
        model.addAttribute("rotation", ((LinkedHashMap<String, Object>) display).get("rotation"));
        model.addAttribute("bootLoader", crashData.getBuild().get("BOOTLOADER"));
        model.addAttribute("buildBrand", crashData.getBuild().get("BRAND"));
        model.addAttribute("CPU_ABI", crashData.getBuild().get("CPU_ABI"));
        model.addAttribute("CPU_ABI2", crashData.getBuild().get("CPU_ABI2"));
        model.addAttribute("buildDisplay", crashData.getBuild().get("DISPLAY"));
        model.addAttribute("TWRP", crashData.getBuild().get("DEVICE"));
        model.addAttribute("model", crashData.getBuild().get("MODEL"));
        model.addAttribute("board", crashData.getBuild().get("BOARD"));
        model.addAttribute("deviceFeatures", deviceFeatures);

        return "crash";
    }

    @RequestMapping(value = "/crashtimefilter/{time}", method = RequestMethod.GET)
    public String crashDataTagView(@RequestParam(value = "time") String time, Model model) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dddd HH:mm:ss.SSS");
        DateTime dt = formatter.parseDateTime(time);

        CrashData crashData = this.repository.findCrashDataByTime(dt);
        List<CrashData> chartTimeData = this.repository.findAll(new Sort(Sort.Direction.ASC, "time"));

        if (crashData == null) {
            return "nodata";
        }

        Object display = crashData.getDisplay().get("0");

        Map<String, Object> deviceFeatures = new LinkedHashMap<String, Object>();
        Set<String> deviceFeaturesKey = crashData.getDeviceFeatures().keySet();

        for (String s : deviceFeaturesKey) {
            deviceFeatures.put(s.replace("-", "."), crashData.getDeviceFeatures().get(s));
        }

        LinkedHashSet<ChartData> chartData = new LinkedHashSet<ChartData>();
        for (CrashData data : chartTimeData) {
            chartData.add(new ChartData(Utility.getChartDataDate(data.getTime()), Utility.getChartDataTime(data.getTime())));
        }

        model.addAttribute("crash", crashData);
        model.addAttribute("chartData", chartData);
        model.addAttribute("time", crashData.getTime());
        model.addAttribute("realSize", ((LinkedHashMap<String, Object>) display).get("realSize"));
        model.addAttribute("rotation", ((LinkedHashMap<String, Object>) display).get("rotation"));
        model.addAttribute("bootLoader", crashData.getBuild().get("BOOTLOADER"));
        model.addAttribute("buildBrand", crashData.getBuild().get("BRAND"));
        model.addAttribute("CPU_ABI", crashData.getBuild().get("CPU_ABI"));
        model.addAttribute("CPU_ABI2", crashData.getBuild().get("CPU_ABI2"));
        model.addAttribute("buildDisplay", crashData.getBuild().get("DISPLAY"));
        model.addAttribute("TWRP", crashData.getBuild().get("DEVICE"));
        model.addAttribute("model", crashData.getBuild().get("MODEL"));
        model.addAttribute("board", crashData.getBuild().get("BOARD"));
        model.addAttribute("deviceFeatures", deviceFeatures);

        return "crash";
    }

    @RequestMapping(value = "/crash", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> crashDataSave(@RequestBody CrashData data) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Set<String> set = data.getDeviceFeatures().keySet();

        for (String s : set) {
            map.put(s.replace(".", "-"), data.getDeviceFeatures().get(s));
        }

        this.repository.save(new CrashData(data.getTime(),
                data.getAndroidVersion(),
                data.getAppVersionCode(),
                data.getAppVersionName(),
                data.getAvailableMemorySize(),
                data.getBrand(),
                data.getLogcat(),
                data.getDeviceID(),
                data.getDisplay(),
                data.getEnvironment(),
                map,
                data.getBuild()));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "Crash Data Transfer Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }
}
