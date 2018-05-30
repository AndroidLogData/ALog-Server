package com.logdata.web.controller;

import com.logdata.common.model.CrashTimeVO;
import com.logdata.common.model.CrashVO;
import com.logdata.common.model.UserVO;
import com.logdata.common.util.Utility;
import com.logdata.web.service.RestAPIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

@Controller
public class CrashDataController {
    private final RestAPIUtility restAPIUtility;
    private final Utility utility;

    @Autowired
    public CrashDataController(RestAPIUtility restAPIUtility, Utility utility) {
        this.restAPIUtility = restAPIUtility;
        this.utility = utility;
    }

    @RequestMapping(value = "/crash", method = RequestMethod.GET)
    public String crashPage(Principal user, Model model, HttpSession session) {
        if (user == null) {
            return "login";
        }

        model.addAttribute("sideMenuItems", getPackageName(user));
        model.addAttribute("packageName", session.getAttribute("packageName"));

        return "crash";
    }

    @RequestMapping(value = "/crash/filter/time/{query}", method = RequestMethod.GET)
    public String crashDataTimeView(Principal user, @RequestParam(value = "time") long time, @RequestParam(value = "package-name") String packageName, Model model, HttpSession session) {
        if (user == null) {
            return "login";
        }

        CrashVO crashVO = restAPIUtility.getChoseCrashTimeData("/crash/filter/time", getUserApiKey(user.getName()), time, packageName);

        if (crashVO == null || !(getUserApiKey(user.getName()).equals(crashVO.getApiKey()))) {
            return "nodata";
        }

        Object display = crashVO.getDisplay().get("0");

        Map<String, Object> deviceFeatures = new LinkedHashMap<String, Object>();
        Set<String> deviceFeaturesKey = crashVO.getDeviceFeatures().keySet();

        for (String s : deviceFeaturesKey) {
            deviceFeatures.put(s.replace("-", "."), crashVO.getDeviceFeatures().get(s));
        }

        model.addAttribute("crash", crashVO);
        model.addAttribute("logcat", utility.logcatSummary(crashVO.getLogcat()));
        model.addAttribute("time", utility.getStringTimeToLong(crashVO.getTime()));
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
        model.addAttribute("packageName", session.getAttribute("packageName"));

        return "crash";
    }

    @RequestMapping(value = "/crash/filter/package-name/{query}", method = RequestMethod.GET)
    public String crashPackageNamePage(Principal user, Model model, @RequestParam(value = "package-name") String packageName, HttpSession session) {
        if (user == null) {
            return "login";
        }

        CrashVO crashVO = restAPIUtility.getCrashData("/crash/filter/package-name", getUserApiKey(user.getName()), packageName);

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

        model.addAttribute("crash", crashVO);
        model.addAttribute("logcat", utility.logcatSummary(crashVO.getLogcat()));
        model.addAttribute("time", utility.getStringTimeToLong(crashVO.getTime()));
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
        model.addAttribute("packageName", session.getAttribute("packageName"));

        return "crash";
    }

    @RequestMapping(value = "/crash", method = RequestMethod.POST)
    public ResponseEntity<Object> crashDataSave(@RequestHeader(value = "apiKey") String apiKey, @RequestBody CrashVO data) {
        return restAPIUtility.postData("/api", "/crash", apiKey, data);
    }

    private ArrayList getPackageName(Principal user) {
        return restAPIUtility.getCrashPackageNameList("/crash/package-name/set", getUserApiKey(user.getName()));
    }

    public String getUserApiKey(String name) {
        UserVO u = this.restAPIUtility.findUser("/find", name);
        return u.getApiKey();
    }

    public ArrayList getCrashTime(Principal user, String packageName) {
        CrashTimeVO[] list = restAPIUtility.getCrashTimeList("/crash/package-name/time", getUserApiKey(user.getName()), packageName);

        return new ArrayList<CrashTimeVO>(Arrays.asList(list));
    }
}
