package com.xxx.ency.di.module;

import com.xxx.ency.di.qualifier.ImageURL;
import com.xxx.ency.di.scope.ActivityScope;
import com.xxx.ency.model.http.ImageApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiarh on 2017/11/29.
 */

@Module
public class ImageActivityModule {

    @ImageURL
    @Provides
    @ActivityScope
    Retrofit provideImageRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return builder
                .baseUrl(ImageApi.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ActivityScope
    ImageApi provideImageApi(@ImageURL Retrofit retrofit) {
        return retrofit.create(ImageApi.class);
    }
}
