package com.xxx.ency.di.module;

import com.xxx.ency.di.qualifier.EyepetizerURL;
import com.xxx.ency.di.scope.FragmentScope;
import com.xxx.ency.model.http.EyepetizerApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiarh on 2018/2/7.
 */
@Module
public class EyepetizerFragmentModule {

    @EyepetizerURL
    @Provides
    @FragmentScope
    Retrofit provideEyepetizerRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return builder
                .baseUrl(EyepetizerApi.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @FragmentScope
    EyepetizerApi provideEyepetizerApi(@EyepetizerURL Retrofit retrofit) {
        return retrofit.create(EyepetizerApi.class);
    }
}
