package com.xxx.ency.config;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.xxx.ency.util.AppApplicationUtil;

/**
 * Created by xiarh on 2017/9/20.
 */

public class EnycApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Bugly
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(String.valueOf(AppApplicationUtil.getVersionCode(getApplicationContext())));
        CrashReport.initCrashReport(getApplicationContext(), "e0359610ba", true);
    }
}
