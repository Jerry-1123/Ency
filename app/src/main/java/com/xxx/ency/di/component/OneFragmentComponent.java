package com.xxx.ency.di.component;

import com.xxx.ency.di.module.OneFragmentModule;
import com.xxx.ency.di.scope.FragmentScope;
import com.xxx.ency.view.one.OneFragment;

import dagger.Component;

/**
 * Created by xiarh on 2017/11/2.
 */

@FragmentScope
@Component(dependencies = AppComponent.class,
        modules = OneFragmentModule.class)
public interface OneFragmentComponent {

    void inject(OneFragment oneFragment);

}
