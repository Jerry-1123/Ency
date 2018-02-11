package com.xxx.ency.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * APP应用退出(按两次)
 * Created by xiarh on 2017/9/13.
 */

public class AppExitUtil {

    private static Boolean isExit = false;

    /**
     * 退出App程序应用
     *
     * @param context 上下文
     * @return boolean True退出|False提示
     */
    public static boolean exitApp(Context context, View view) {
        Timer tExit;
        if (isExit == false) {
            isExit = true;
            SnackBarUtils.show(view,"再按一次退出");
            //创建定时器
            tExit = new Timer();
            //如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    //取消退出
                    isExit = false;
                }
            }, 2000);
        } else {
            AppActivityTaskManager.getInstance().removeAllActivity();
            //创建ACTION_MAIN
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //启动ACTION_MAIN，直接回到桌面
            context.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        LogUtil.i("AppExitUtil-->>exitApp：", isExit + "");
        LogUtil.i("AppExitUtil-->>exitApp：", "最大内存：" + Runtime.getRuntime().maxMemory());
        LogUtil.i("AppExitUtil-->>exitApp：", "占用内存：" + Runtime.getRuntime().totalMemory());
        LogUtil.i("AppExitUtil-->>exitApp：", "空闲内存：" + Runtime.getRuntime().freeMemory());
        return isExit;
    }

    /**
     * 直接退出
     *
     * @param context
     */
    public static void exitAPP(Context context) {
        AppActivityTaskManager.getInstance().removeAllActivity();
        //创建ACTION_MAIN
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //启动ACTION_MAIN，直接回到桌面
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
