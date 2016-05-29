package com.learn.ThreadEx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ajay on 24-May-16.
 */

public class ThreadUtil {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    static {
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
    }

    public static String getDateFormat(Date d) {
        return sdf.format(d);
    }

    public static void printStr(String s) {
        System.out.println("[" + getDateFormat(new Date()) + "] - {" + Thread.currentThread().getName() + "} - " + s);
    }
}
