package com.logdata.backend.controller;

import com.logdata.backend.model.ChartVO;
import com.logdata.backend.model.CrashVO;
import com.logdata.backend.model.UserVO;
import com.logdata.backend.repository.CrashDataRepository;
import com.logdata.backend.repository.UserDataRepository;
import com.logdata.backend.util.Utility;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
public class CrashController {
    @Autowired
    private CrashDataRepository crashDataRepository;
    @Autowired
    private UserDataRepository userDataRepository;

    @RequestMapping(value = "/crash", method = RequestMethod.GET)
    public String crashPage(Principal user, Model model) {
        if (user == null) {
            model.addAttribute("noData", true);
            return "nodata";
        }
        UserVO u = this.userDataRepository.findByUserID(user.getName());

        CrashVO crashVO = this.crashDataRepository.findCrashDataBy(new Sort(Sort.Direction.DESC, "time"));
        List<CrashVO> chartTimeData = this.crashDataRepository.findByApiKeyOrderByTimeDesc(u.getApiKey(), new Sort(Sort.Direction.ASC, "time"));

        if (crashVO == null || chartTimeData.size() == 0) {
            model.addAttribute("noData", true);
            return "nodata";
        }

        Object display = crashVO.getDisplay().get("0");

        Map<String, Object> deviceFeatures = new LinkedHashMap<String, Object>();
        Set<String> deviceFeaturesKey = crashVO.getDeviceFeatures().keySet();

        for (String s : deviceFeaturesKey) {
            deviceFeatures.put(s.replace("-", "."), crashVO.getDeviceFeatures().get(s));
        }

        LinkedHashSet<ChartVO> chartData = new LinkedHashSet<ChartVO>();
        for (CrashVO data : chartTimeData) {
            chartData.add(new ChartVO(Utility.getChartDataDate(data.getTime()), Utility.getChartDataTime(data.getTime())));
        }

        model.addAttribute("crash", crashVO);
        model.addAttribute("chartData", chartData);
        model.addAttribute("time", crashVO.getTime());
        model.addAttribute("realSize", ((LinkedHashMap<String, Object>) display).get("realSize"));
        model.addAttribute("rotation", ((LinkedHashMap<String, Object>) display).get("rotation"));
        model.addAttribute("bootLoader", crashVO.getBuild().get("BOOTLOADER"));
        model.addAttribute("buildBrand", crashVO.getBuild().get("BRAND"));
        model.addAttribute("CPU_ABI", crashVO.getBuild().get("CPU_ABI"));
        model.addAttribute("CPU_ABI2", crashVO.getBuild().get("CPU_ABI2"));
        model.addAttribute("buildDisplay", crashVO.getBuild().get("DISPLAY"));
        model.addAttribute("TWRP", crashVO.getBuild().get("DEVICE"));
        model.addAttribute("model", crashVO.getBuild().get("MODEL"));
        model.addAttribute("board", crashVO.getBuild().get("BOARD"));
        model.addAttribute("deviceFeatures", deviceFeatures);

        return "crash";
    }

    @RequestMapping(value = "/crashtimefilter/{time}", method = RequestMethod.GET)
    public String crashDataTagView(Principal user, @RequestParam(value = "time") String time, Model model) {
        if (user == null) {
            model.addAttribute("noData", true);
            return "crash";
        }
        UserVO u = this.userDataRepository.findByUserID(user.getName());

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dddd HH:mm:ss.SSS");
        DateTime dt = formatter.parseDateTime(time);

        CrashVO crashVO = this.crashDataRepository.findCrashDataByTime(dt);
        List<CrashVO> chartTimeData = this.crashDataRepository.findByApiKeyOrderByTimeDesc(u.getApiKey(), new Sort(Sort.Direction.ASC, "time"));

        if (crashVO == null || u.getApiKey().equals(crashVO.getApiKey())) {
            return "nodata";
        }

        Object display = crashVO.getDisplay().get("0");

        Map<String, Object> deviceFeatures = new LinkedHashMap<String, Object>();
        Set<String> deviceFeaturesKey = crashVO.getDeviceFeatures().keySet();

        for (String s : deviceFeaturesKey) {
            deviceFeatures.put(s.replace("-", "."), crashVO.getDeviceFeatures().get(s));
        }

        LinkedHashSet<ChartVO> chartData = new LinkedHashSet<ChartVO>();
        for (CrashVO data : chartTimeData) {
            chartData.add(new ChartVO(Utility.getChartDataDate(data.getTime()), Utility.getChartDataTime(data.getTime())));
        }

        model.addAttribute("crash", crashVO);
        model.addAttribute("chartData", chartData);
        model.addAttribute("time", crashVO.getTime());
        model.addAttribute("realSize", ((LinkedHashMap<String, Object>) display).get("realSize"));
        model.addAttribute("rotation", ((LinkedHashMap<String, Object>) display).get("rotation"));
        model.addAttribute("bootLoader", crashVO.getBuild().get("BOOTLOADER"));
        model.addAttribute("buildBrand", crashVO.getBuild().get("BRAND"));
        model.addAttribute("CPU_ABI", crashVO.getBuild().get("CPU_ABI"));
        model.addAttribute("CPU_ABI2", crashVO.getBuild().get("CPU_ABI2"));
        model.addAttribute("buildDisplay", crashVO.getBuild().get("DISPLAY"));
        model.addAttribute("TWRP", crashVO.getBuild().get("DEVICE"));
        model.addAttribute("model", crashVO.getBuild().get("MODEL"));
        model.addAttribute("board", crashVO.getBuild().get("BOARD"));
        model.addAttribute("deviceFeatures", deviceFeatures);

        return "crash";
    }

    @RequestMapping(value = "/crash", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> crashDataSave(@RequestHeader(value = "secretKey") String secretKey, @RequestBody CrashVO data) {
        if (Utility.CheckedSecretKey(secretKey)) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
            Map<String, String> result = new HashMap<String, String>();
            result.put("result", "Need API Key");

            return new ResponseEntity<>(result, responseHeaders, HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Set<String> set = data.getDeviceFeatures().keySet();

        for (String s : set) {
            map.put(s.replace(".", "-"), data.getDeviceFeatures().get(s));
        }

        this.crashDataRepository.save(new CrashVO(
                data.getPackageName(),
                data.getTime(),
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
                data.getBuild(),
                secretKey));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "Crash Data Transfer Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }
}
