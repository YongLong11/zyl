package com.zyl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName：DateUtil
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/4 20:11
 **/
public class DateUtils {
    public static final String FORMAT_ALL = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_MIN = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YYYYMMddHHMM = "yyyyMMddHHmm";

    public static final int MINUTE_DIMENSIONALITY = 0;
    public static final int HOUR_DIMENSIONALITY = 1;

    public static String dateToString(Date time) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(time);
    }

    public static String dateToString(Date time, String pattern) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(pattern);
        return formatter.format(time);
    }

    public static long stringToLong(String time) throws ParseException {
        return stringToDate(time, FORMAT_ALL).getTime()/1000;
    }

    public static long stringToLong(String time, String pattern) throws ParseException {
        return stringToDate(time, pattern).getTime()/1000;
    }


    public static Date stringToDate(String time) throws ParseException {
        return stringToDate(time, FORMAT_ALL);
    }

    public static Date stringToDate(String time, String pattern) throws ParseException {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(pattern);

        return formatter.parse(time);
    }
}
