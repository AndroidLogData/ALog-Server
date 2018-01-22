package com.logdata.logcat.controller;

import com.logdata.logcat.model.ChartData;
import com.logdata.logcat.model.CrashData;
import com.logdata.logcat.repository.CrashDataRepository;
import com.logdata.logcat.util.Utility;
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

import java.util.*;

@Controller
public class CrashController {
    @Autowired
    private CrashDataRepository repository;

    @RequestMapping(value = "/crash", method = RequestMethod.GET)
    public String crashPage(Model model) {
//        List<CrashData> crashData = this.repository.findAll(new Sort(Sort.Direction.DESC, "time"));
        List<CrashData> crashData = this.repository.findAll(new Sort(Sort.Direction.ASC, "time"));

        if (Utility.isNoData(crashData)) {
            return "nodata";
        }

        Object display = crashData.get(0).getDisplay().get("0");

        Map<String, Object> deviceFeatures = new LinkedHashMap<String, Object>();
        Set<String> deviceFeaturesKey = crashData.get(0).getDeviceFeatures().keySet();

        for (String s : deviceFeaturesKey) {
            deviceFeatures.put(s.replace("-", "."), crashData.get(0).getDeviceFeatures().get(s));
        }

        HashSet<ChartData> chartData = new HashSet<ChartData>();
        for (CrashData data : crashData) {
            chartData.add(new ChartData(Utility.getX(data.getTime()), Utility.getY(data.getTime())));
        }

        model.addAttribute("crash", this.repository.findAll());
        model.addAttribute("chartData", chartData);
        model.addAttribute("time", Utility.getTime(crashData.get(0).getTime()));
        model.addAttribute("realSize", ((LinkedHashMap<String, Object>) display).get("realSize"));
        model.addAttribute("rotation", ((LinkedHashMap<String, Object>) display).get("rotation"));
        model.addAttribute("bootLoader", crashData.get(0).getBuild().get("BOOTLOADER"));
        model.addAttribute("buildBrand", crashData.get(0).getBuild().get("BRAND"));
        model.addAttribute("CPU_ABI", crashData.get(0).getBuild().get("CPU_ABI"));
        model.addAttribute("CPU_ABI2", crashData.get(0).getBuild().get("CPU_ABI2"));
        model.addAttribute("buildDisplay", crashData.get(0).getBuild().get("DISPLAY"));
        model.addAttribute("TWRP", crashData.get(0).getBuild().get("DEVICE"));
        model.addAttribute("model", crashData.get(0).getBuild().get("MODEL"));
        model.addAttribute("board", crashData.get(0).getBuild().get("BOARD"));
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
        result.put("result", "success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }
}
