package com.logdata.logcat.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

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
        if (secretKey.equals("")) {
            return true;
        }
        return false;
    }
}
