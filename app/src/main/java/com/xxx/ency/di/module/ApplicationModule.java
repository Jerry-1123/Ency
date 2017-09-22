package com.xxx.ency.di.module;

import android.content.Context;

import com.xxx.ency.config.EnycApplication;
import com.xxx.ency.model.prefs.SharePrefManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiarh on 2017/9/21.
 */

@Module
public class ApplicationModule {

    private final EnycApplication application;

    public ApplicationModule(EnycApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    SharePrefManager provideSharePrefManager(Context context) {
        return new SharePrefManager(context);
    }
}