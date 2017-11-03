package com.xxx.ency.di.module;

import com.xxx.ency.di.qualifier.BingURL;
import com.xxx.ency.di.scope.ActivityScope;
import com.xxx.ency.model.http.BingApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiarh on 2017/11/3.
 */

@Module
public class AboutActivityModule {

    @BingURL
    @Provides
    @ActivityScope
    Retrofit provideBingRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return builder
                .baseUrl(BingApi.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ActivityScope
    BingApi provideBingApi(@BingURL Retrofit retrofit) {
        return retrofit.create(BingApi.class);
    }
}
