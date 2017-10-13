package com.xxx.ency.di.module;

import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xxx.ency.di.qualifier.UpdateURL;
import com.xxx.ency.di.qualifier.WeatherURL;
import com.xxx.ency.di.scope.ActivityScope;
import com.xxx.ency.model.http.UpdateApi;
import com.xxx.ency.model.http.WeatherApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiarh on 2017/10/10.
 */

@Module
public class MainActivityModule {

    private Activity activity;

    public MainActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    RxPermissions provideRxPermissions(Activity activity) {
        return new RxPermissions(activity);
    }

    @WeatherURL
    @Provides
    @ActivityScope
    Retrofit provideWeatherRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return builder
                .baseUrl(WeatherApi.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ActivityScope
    WeatherApi provideWeatherApi(@WeatherURL Retrofit retrofit) {
        return retrofit.create(WeatherApi.class);
    }

    @UpdateURL
    @Provides
    @ActivityScope
    Retrofit provideUpdateRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return builder
                .baseUrl(UpdateApi.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ActivityScope
    UpdateApi provideUpdateApi(@UpdateURL Retrofit retrofit) {
        return retrofit.create(UpdateApi.class);
    }
}
