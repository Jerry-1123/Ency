package com.xxx.ency.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转化工具类
 * Created by xiarh on 2017/9/14.
 */

public class DateUtil {

    /**
     * 毫秒值转化为时间
     *
     * @param time 时间毫秒
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String Long2String(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 时间转化为字符串
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String Date2String(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 字符串转化为时间
     *
     * @param time 2010-11-20 11:10:10
     * @return Date
     */
    public static Date String2Date(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
