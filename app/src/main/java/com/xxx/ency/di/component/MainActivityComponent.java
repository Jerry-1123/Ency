package com.xxx.ency.di.component;

import com.xxx.ency.di.module.MainActivityModule;
import com.xxx.ency.di.scope.ActivityScope;
import com.xxx.ency.view.main.MainActivity;

import dagger.Component;

/**
 * Created by xiarh on 2017/10/10.
 */

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = MainActivityModule.class )
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);

}
