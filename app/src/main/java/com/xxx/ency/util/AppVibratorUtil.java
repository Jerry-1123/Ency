package com.xxx.ency.util;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * 手机震动工具类
 * Created by xiarh on 2017/9/14.
 * 使用必须添加权限：<uses-permission android:name="android.permission.VIBRATE" />
 */
public class AppVibratorUtil {

    /**
     * Context context  ：上下文
     * long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * 举个栗子：
     * 在程序起动后等待3秒后，振动1秒，再等待2秒后，振动5秒，再等待3秒后，振动1秒
     * long[] pattern = {3000, 1000, 2000, 5000, 3000, 1000}; // OFF/ON/OFF/ON…
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void Vibrate(Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    public static void Vibrate(Context context, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
}
