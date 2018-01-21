package com.logdata.logcat.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Utility {
    public static String getTime(long time) {
        DateTime date = new DateTime(time);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        return date.toString(fmt);
    }
}
