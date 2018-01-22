package com.logdata.logcat.util;

import com.logdata.logcat.model.LogData;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

public class Utility {
    public static String getTime(long time) {
        DateTime date = new DateTime(time);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        return date.toString(fmt);
    }

    public static String getX(long time) {
        DateTime date = new DateTime(time);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        return date.toString(fmt);
    }

    public static String getY(long time) {
        DateTime date = new DateTime(time);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");

        return date.toString(fmt);
    }

    public static boolean isNoData(List<?> list) {
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    public static void setStringTime(List<LogData> logData) {
        for (LogData data : logData) {
            data.setStringTime(Utility.getTime(data.getTime()));
        }
    }
}
