package com.logdata.common.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static String getTime(DateTime time) {
        DateTime date = new DateTime(time);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

        return date.toString(fmt);
    }

    public static String getChartDataDate(DateTime time) {
        DateTime date = new DateTime(time);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        return date.toString(fmt);
    }

    public static String getChartDataTime(DateTime time) {
        DateTime date = new DateTime(time);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss.SSS");

        return date.toString(fmt);
    }

    public static boolean CheckedSecretKey(String secretKey) {
        if (secretKey.equals("") || secretKey.equals("\"\"")) {
            return true;
        }
        return false;
    }

    public static String findCrashName(String logcat) {
//        Pattern pattern = Pattern.compile("[0-9].*?(ACRA).*?(?i)(Exception|Error).*?(\\s|[a-zA-z]|\\.)+");
        Pattern pattern = Pattern.compile("[a-zA-Z]+(Exception|Error).*?");
        Matcher matcher = pattern.matcher(logcat);

        if (!matcher.find()) {
            return "null";
        } else {
            return matcher.group(0);
        }
    }

    public static String logcatSummary(String logcat) {
        Pattern pattern = Pattern.compile("([0-9]{1,2}-[0-9]{1,2}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}\\.[0-9]{1,3}).*?(ACRA).*?\\s*(\\s|\\(|\\)|:|[0-9])*(\\s|[a-zA-Z])*(\\.|_|\\$|[0-9a-zA-Z])*(\\([a-zA-Z]*.[a-zA-Z]*:[0-9]*\\))*(\\([a-zA-Z]*\\s*[a-zA-Z]*\\))*");
        Matcher matcher = pattern.matcher(logcat);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            result.append(matcher.group() + "\n");
        }

        return result.toString();
    }
}
