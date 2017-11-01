package com.xxx.ency.di.module;

import com.xxx.ency.di.qualifier.OneURL;
import com.xxx.ency.di.scope.FragmentScope;
import com.xxx.ency.model.http.OneApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiarh on 2017/11/1.
 */

@Module
public class OneFragmentModule {

    @OneURL
    @Provides
    @FragmentScope
    Retrofit provideOneRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return builder
                .baseUrl(OneApi.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @FragmentScope
    OneApi provideOneApi(@OneURL Retrofit retrofit) {
        return retrofit.create(OneApi.class);
    }
}
