package com.xxx.ency.config;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.xxx.ency.di.component.AppComponent;
import com.xxx.ency.di.component.DaggerAppComponent;
import com.xxx.ency.di.module.ApplicationModule;
import com.xxx.ency.di.module.HttpModule;
import com.xxx.ency.util.AppApplicationUtil;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Created by xiarh on 2017/9/20.
 */

public class EncyApplication extends Application {

    private static EncyApplication instance;

    public static AppComponent appComponent;

    public static synchronized EncyApplication getInstance() {
        return instance;
    }

    public static RefWatcher getRefWatcher(Context context) {
        EncyApplication application = (EncyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        appComponent = DaggerAppComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();

        // 初始化Bugly
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(String.valueOf(AppApplicationUtil.getVersionCode(getApplicationContext())));
        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, true); // debug版本设置为true，正式发布设置为false

        // 初始化Fragmentation
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出  默认NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                // 开发环境：true时，遇到异常："Can not perform this action after onSaveInstanceState!"时，抛出，并Crash;
                // 生产环境：false时，不抛出，不会Crash，会捕获，可以在handleException()里监听到
                .debug(true)
                // 实际场景建议.debug(BuildConfig.DEBUG)
                // 生产环境时，捕获上述异常（避免crash），会捕获
                // 建议在回调处上传下面异常到崩溃监控服务器
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        CrashReport.postCatchedException(e);  // bugly会将这个Exception上报
                    }
                })
                .install();

        // 初始化LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}