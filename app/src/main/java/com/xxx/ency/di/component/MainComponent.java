package com.xxx.ency.di.component;

import com.xxx.ency.di.module.MainModule;
import com.xxx.ency.di.scope.PerActivity;
import com.xxx.ency.view.main.MainActivity;

import dagger.Component;

/**
 * Created by xiarh on 2017/9/25.
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);

}
