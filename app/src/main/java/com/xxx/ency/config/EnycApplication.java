package com.xxx.ency.config;

import android.app.Application;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.xxx.ency.di.component.AppComponent;
import com.xxx.ency.di.component.DaggerAppComponent;
import com.xxx.ency.di.module.ApplicationModule;
import com.xxx.ency.di.module.HttpModule;
import com.xxx.ency.model.prefs.SharePrefManager;
import com.xxx.ency.util.AppApplicationUtil;

import javax.inject.Inject;

/**
 * Created by xiarh on 2017/9/20.
 */

public class EnycApplication extends Application {

    private static EnycApplication instance;

    public static AppComponent appComponent;

    public static synchronized EnycApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        appComponent = DaggerAppComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule(this))
                .build();

        // 初始化Bugly
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(String.valueOf(AppApplicationUtil.getVersionCode(getApplicationContext())));
        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, true); // debug版本设置为true，正式发布设置为false
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}