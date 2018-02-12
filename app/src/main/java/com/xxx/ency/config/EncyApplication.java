package com.xxx.ency.config;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.xxx.ency.BuildConfig;
import com.xxx.ency.di.component.AppComponent;
import com.xxx.ency.di.component.DaggerAppComponent;
import com.xxx.ency.di.module.ApplicationModule;
import com.xxx.ency.di.module.HttpModule;
import com.xxx.ency.model.prefs.SharePrefManager;
import com.xxx.ency.util.AppApplicationUtil;
import com.xxx.ency.util.LogUtil;

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

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        appComponent = DaggerAppComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();

        SharePrefManager sharePrefManager = appComponent.getSharePrefManager();

        boolean nightMode = sharePrefManager.getNightMode();
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        sharePrefManager.setLocalMode(AppCompatDelegate.getDefaultNightMode());
        sharePrefManager.setLocalProvincialTrafficPatterns(sharePrefManager.getProvincialTrafficPattern());

        // 初始化Bugly
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(String.valueOf(AppApplicationUtil.getVersionCode(getApplicationContext())));
        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, false); // debug版本设置为true，正式发布设置为false

        // 初始化Fragmentation
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出  默认NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                // 开发环境：true时，遇到异常："Can not perform this action after onSaveInstanceState!"时，抛出，并Crash;
                // 生产环境：false时，不抛出，不会Crash，会捕获，可以在handleException()里监听到
                .debug(false)
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

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

        // 初始化LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        // 异常处理类
        if (!BuildConfig.DEBUG) {
            EncyCrashHandler.getInstance().setCrashHanler(this);
        }
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}