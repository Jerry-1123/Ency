package com.xxx.ency.di.component;

import com.xxx.ency.di.module.ImageActivityModule;
import com.xxx.ency.di.scope.ActivityScope;
import com.xxx.ency.view.gank.ImageActivity;

import dagger.Component;

/**
 * Created by xiarh on 2017/11/29.
 */

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = ImageActivityModule.class)
public interface ImageActivityComponent {

    void inject(ImageActivity imageActivity);

}
