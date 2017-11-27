package com.xxx.ency.di.component;

import com.xxx.ency.di.module.GankFragmentModule;
import com.xxx.ency.di.scope.FragmentScope;
import com.xxx.ency.view.gank.GankFragment;

import dagger.Component;

/**
 * Created by xiarh on 2017/11/27.
 */

@FragmentScope
@Component(dependencies = AppComponent.class,
        modules = GankFragmentModule.class)
public interface GankFragmentComponent {

    void inject(GankFragment gankFragment);

}
