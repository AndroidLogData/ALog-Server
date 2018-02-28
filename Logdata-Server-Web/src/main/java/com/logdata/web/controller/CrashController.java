package com.logdata.web.controller;

import com.logdata.common.model.CrashTimeVO;
import com.logdata.common.model.CrashVO;
import com.logdata.common.model.UserVO;
import com.logdata.common.repository.CrashDataRepository;
import com.logdata.common.repository.UserDataRepository;
import com.logdata.common.util.Utility;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
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
            return "login";
        }

        model.addAttribute("noData", true);
        model.addAttribute("sideMenuItems", getPackageName(user));

        return "crash";
    }

    @RequestMapping(value = "/crashtimefilter/{time}", method = RequestMethod.GET)
    public String crashDataTimeView(Principal user, @RequestParam(value = "time") String time, @RequestParam(value = "packageName") String packageName, Model model) {
        if (user == null) {
            return "login";
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dddd HH:mm:ss.SSS");
        DateTime dt = formatter.parseDateTime(time);

        CrashVO crashVO = this.crashDataRepository.findCrashDataByTimeAndApiKeyAndPackageName(dt, getUserApiKey(user), packageName);

        if (crashVO == null || !(getUserApiKey(user).equals(crashVO.getApiKey()))) {
            return "nodata";
        }

        Object display = crashVO.getDisplay().get("0");

        Map<String, Object> deviceFeatures = new LinkedHashMap<String, Object>();
        Set<String> deviceFeaturesKey = crashVO.getDeviceFeatures().keySet();

        for (String s : deviceFeaturesKey) {
            deviceFeatures.put(s.replace("-", "."), crashVO.getDeviceFeatures().get(s));
        }

        model.addAttribute("noData", false);
        model.addAttribute("crash", crashVO);
        model.addAttribute("logcat", Utility.logcatSummary(crashVO.getLogcat()));
        model.addAttribute("time", Utility.timeTranslate(crashVO.getTime()));
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
        model.addAttribute("timeData", getCrashTime(user, packageName));
        model.addAttribute("sideMenuItems", getPackageName(user));

        return "crash";
    }

    @RequestMapping(value = "/crashpackagenamefilter/{packageName}", method = RequestMethod.GET)
    public String crashPackageNamePage(Principal user, Model model, @RequestParam(value = "packageName") String packageName) {
        if (user == null) {
            return "login";
        }

        CrashVO crashVO = this.crashDataRepository.findCrashDataByPackageNameAndApiKeyOrderByTimeDesc(packageName, getUserApiKey(user));

        if (crashVO == null) {
            model.addAttribute("noData", true);
            return "crash";
        }

        Object display = crashVO.getDisplay().get("0");

        Map<String, Object> deviceFeatures = new LinkedHashMap<String, Object>();
        Set<String> deviceFeaturesKey = crashVO.getDeviceFeatures().keySet();

        for (String s : deviceFeaturesKey) {
            deviceFeatures.put(s.replace("-", "."), crashVO.getDeviceFeatures().get(s));
        }

        model.addAttribute("noData", false);
        model.addAttribute("crash", crashVO);
        model.addAttribute("logcat", Utility.logcatSummary(crashVO.getLogcat()));
        model.addAttribute("time", Utility.timeTranslate(crashVO.getTime()));
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
        model.addAttribute("timeData", getCrashTime(user, packageName));
        model.addAttribute("sideMenuItems", getPackageName(user));

        return "crash";
    }

    private Set<String> getPackageName(Principal user) {
        List<CrashVO> setData = this.crashDataRepository.findByApiKey(getUserApiKey(user));

        Set<String> packageNameSet = new HashSet<String>();

        for (CrashVO data : setData) {
            packageNameSet.add(data.getPackageName());
        }

        return packageNameSet;
    }

    public String getUserApiKey(Principal user) {
        UserVO u = this.userDataRepository.findByUserID(user.getName());
        return u.getApiKey();
    }

    public ArrayList<CrashTimeVO> getCrashTime(Principal user, String packageName) {
        ArrayList<CrashVO> list = this.crashDataRepository.findByApiKeyAndPackageNameOrderByTimeAsc(getUserApiKey(user), packageName);
        ArrayList<CrashTimeVO> crashTimeVOs = new ArrayList<CrashTimeVO>();

        for (int i = 0; i < list.size(); i++) {
            crashTimeVOs.add(new CrashTimeVO(packageName, Utility.timeTranslate(list.get(i).getTime())));
        }

        return crashTimeVOs;
    }

    @RequestMapping(value = "/crash", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> crashDataSave(@RequestHeader(value = "secretKey") String secretKey, @RequestBody CrashVO data) {
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
        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "Crash Data Transfer Success");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }
}
