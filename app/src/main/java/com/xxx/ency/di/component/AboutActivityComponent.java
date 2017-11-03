package com.xxx.ency.di.component;

import com.xxx.ency.di.module.AboutActivityModule;
import com.xxx.ency.di.scope.ActivityScope;
import com.xxx.ency.view.about.AboutActivity;

import dagger.Component;

/**
 * Created by xiarh on 2017/11/3.
 */

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = AboutActivityModule.class)
public interface AboutActivityComponent {

    void inject(AboutActivity aboutActivity);

}
