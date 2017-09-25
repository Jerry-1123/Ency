package com.xxx.ency.di.module;

import com.xxx.ency.di.scope.PerActivity;
import com.xxx.ency.model.http.api.WeatherApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by xiarh on 2017/9/25.
 */
@Module
public class MainModule {

    @Provides
    Retrofit retrofit(Retrofit.Builder builder) {
        return builder.baseUrl(WeatherApi.Host).build();
    }

    @Provides
    WeatherApi provideWeatherApi(Retrofit retrofit) {
        return retrofit.create(WeatherApi.class);
    }
}
