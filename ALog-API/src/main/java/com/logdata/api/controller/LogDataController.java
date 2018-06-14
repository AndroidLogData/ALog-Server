package com.logdata.api.controller;

import com.logdata.api.sevice.LogDataService;
import com.logdata.api.sevice.PackageNameDataService;
import com.logdata.common.logger.Logger;
import com.logdata.common.model.LogVO;
import com.logdata.common.model.PackageNameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 로그 데이터를 관리하는 컨트롤러
 */
@Controller
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(value = "/api")
public class LogDataController {
    private final LogDataService logDataService;
    private final PackageNameDataService packageNameDataService;

    @Autowired
    public LogDataController(LogDataService logDataService, PackageNameDataService packageNameDataService) {
        this.logDataService = logDataService;
        this.packageNameDataService = packageNameDataService;
    }

    /**
     * 안드로이드에서 전달 받은 로그 데이터를 데이터베이스에 넣는 메소드
     * @param apiKey 로그 데이터 식별자
     * @param data   로그 데이터 정보
     * @return 데이터 베이스에 정보가 정상적으로 삽입 되었는지에 대한 정보
     */
    @RequestMapping(value = "/log-data", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> logDataSave(@RequestHeader(value = "apiKey") String apiKey, @RequestBody LogVO data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();

        if (apiKey.equals("")) {
            responseHeaders = new HttpHeaders();
            result.put("result", "Need API Key");
            Logger.e("Need API Key");
            return new ResponseEntity<>(result, responseHeaders, HttpStatus.FORBIDDEN);
        }

        this.logDataService.save(
                new LogVO(
                        data.getPackageName(),
                        data.getLevel(),
                        data.getTag(),
                        data.getMessage(),
                        data.getTime(),
                        data.getMemoryInfo()
                )
        );

        PackageNameVO packageNameList = packageNameDataService.findPackageNameVOByApiKey(apiKey);

        if (packageNameList.getPackageNameList().size() == 0) {
            packageNameDataService.insertPackageName(apiKey, data.getPackageName());
        } else {
            boolean flag = false;
            for (int i = 0; i < packageNameList.getPackageNameList().size(); i++) {
                if (data.getPackageName().equals(packageNameList.getPackageNameList().get(i))) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                packageNameDataService.insertPackageName(apiKey, data.getPackageName());
            }
        }

        responseHeaders = new HttpHeaders();
        result.put("result", "Log Data Transfer Success");
        Logger.i("Log Data Input Success");
        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }

    /**
     * 로그 데이터 제거하는 메소드
     * @param apiKey      로그 데이터 식별자
     * @param packageName 지우고 싶은 로그 데이터의 패키지 이름
     * @return 정상적으로 제거 되었는지 정보
     */
    @RequestMapping(value = "/log-data/{query}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteLogData(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
        Map<String, String> result = new HashMap<String, String>();

        for (String item : packageNameVO.getPackageNameList()) {
            if (item.equals(packageName)) {
                this.logDataService.delete(item);
                packageNameVO.getPackageNameList().remove(item);
                this.packageNameDataService.save(packageNameVO);
                Logger.i("delete log data success");

                responseHeaders = new HttpHeaders();
                result.put("result", "Log Data Delete Success");

                return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
            }
        }

        responseHeaders = new HttpHeaders();
        result.put("result", "Log Data Delete Fail");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.BAD_REQUEST);
    }

    /**
     * 로그 데이터를 레벨별로 필터링하는 메소드
     * @param apiKey 로그 데이터 식별자
     * @param packageName 정보를 찾고 싶은 로그 데이터 패키지 이름
     * @param level 찾고 싶은 레벨 정보
     * @return 레벨별로 필터링된 로그 데이터
     */
    @RequestMapping(value = "/log-data/filter/level/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<LogVO> logDataLevelList(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName, @RequestParam(value = "level") String level) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);
        for (int i = 0; i < packageNameVO.getPackageNameList().size(); i++) {
            if (packageName.equals(packageNameVO.getPackageNameList().get(i))) {
                return this.logDataService.findByPackageNameAndLevel(packageName, level, new Sort(Sort.Direction.DESC, "time"));
            }
        }
        return null;
    }

    /**
     * 로그 데이터를 태크별로 필터링하는 메소드
     * @param apiKey 로그 데이터 식별자
     * @param packageName 정보를 찾고 싶은 로그 데이터 패키지 이름
     * @param tag 찾고 싶은 태그 정보
     * @return 태그별로 필터링된 로그 데이터
     */
    @RequestMapping(value = "/log-data/filter/tag/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<LogVO> logDataTagList(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName, @RequestParam(value = "tag") String tag) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);
        for (int i = 0; i < packageNameVO.getPackageNameList().size(); i++) {
            if (packageName.equals(packageNameVO.getPackageNameList().get(i))) {
                List<LogVO> logVOList = this.logDataService.findByPackageName(packageName, new Sort(Sort.Direction.DESC, "time"));
                List<LogVO> result = new ArrayList<>();

                for (LogVO item : logVOList) {
                    Pattern pattern = Pattern.compile("\\([0-9]*\\)");
                    Matcher matcher = pattern.matcher(item.getTag());
                    String str = matcher.replaceFirst("");
                    if (tag.equals(str)) {
                        result.add(item);
                    }
                }

                return result;
            }
        }
        return null;
    }

    /**
     * 로그 데이터를 필터링 할 수 있는 태그 이름 찾는 메소드
     * @param apiKey 로그 데이터 식별자
     * @param packageName 태그 데이터를 찾고 싶은 패키지 이름
     * @return 태그 정보를 담은 Set
     */
    @RequestMapping(value = "/log-data/tag/set/{query}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    private Set<String> getTag(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);

        for (int i = 0; i < packageNameVO.getPackageNameList().size(); i++) {
            if (packageNameVO.getPackageNameList().get(i).equals(packageName)) {
                List<LogVO> logList = this.logDataService.findByPackageName(packageName);
                Set<String> tagSet = new HashSet<String>();
                for (LogVO logData : logList) {
                    Pattern pattern = Pattern.compile("\\([0-9]*\\)");
                    Matcher matcher = pattern.matcher(logData.getTag());
                    String str = matcher.replaceFirst("");
                    tagSet.add(str);
                }
                return tagSet;
            }
        }

        return null;
    }

    /**
     * 로그 데이터를 필터링 할 수 있는 패키지 이름 찾는 메소드
     * @param apiKey 로그 데이터 식별자
     * @return 패키지 정보를 담은 ArrayList
     */
    @RequestMapping(value = "/log-data/package-name/set", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    private ArrayList<String> getPackageName(@RequestHeader(value = "apiKey") String apiKey) {
        return this.packageNameDataService.findPackageNameVOByApiKey(apiKey).getPackageNameList();
    }

    /**
     * 패키지 이름별로 필터링된 로그 데이터를 찾는 메소드
     * @param apiKey 로그 데이터 식별자
     * @param packageName 필터링 하고 싶은 패키지 이름
     * @return 패키지 이름별로 필터링된 List
     */
    @RequestMapping(value = "/log-data/filter/package-name/{query}", method = RequestMethod.GET)
    @ResponseBody
    public List<LogVO> logDataPackageNameList(@RequestHeader(value = "apiKey") String apiKey, @RequestParam(value = "package-name") String packageName) {
        PackageNameVO packageNameVO = this.packageNameDataService.findPackageNameVOByApiKey(apiKey);
        if (packageNameVO == null) {
            return null;
        }
        return this.logDataService.findByPackageName(packageName, new Sort(Sort.Direction.DESC, "time"));
    }

    /**
     * 모든 로그 데이터를 반환하는 메소드
     * @return 모든 로그 데이터가 담긴 List
     */
    @RequestMapping(value = "/log-data/list", method = RequestMethod.GET)
    @ResponseBody
    public List<LogVO> allLogData() {
        return this.logDataService.findAll();
    }
}
