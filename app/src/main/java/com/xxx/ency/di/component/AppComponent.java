package com.xxx.ency.di.component;

import android.content.Context;

import com.xxx.ency.di.module.ApplicationModule;
import com.xxx.ency.di.module.HttpModule;
import com.xxx.ency.model.db.GreenDaoManager;
import com.xxx.ency.model.prefs.SharePrefManager;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by xiarh on 2017/9/21.
 */

@Singleton
@Component(modules = {ApplicationModule.class, HttpModule.class})
public interface AppComponent {

    Context getContext(); // 提供Context给子Component使用

    SharePrefManager getSharePrefManager();

    Retrofit.Builder getRetrofitBuilder();

    OkHttpClient getOkHttpClient();

    GreenDaoManager getGreenDaoManager();

}