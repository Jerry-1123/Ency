package com.xxx.ency.di.component;

import com.xxx.ency.di.module.EyepetizerFragmentModule;
import com.xxx.ency.di.scope.FragmentScope;
import com.xxx.ency.view.eyepetizer.EyepetizerFragment;

import dagger.Component;

/**
 * Created by xiarh on 2018/2/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class,
        modules = EyepetizerFragmentModule.class)
public interface EyepetizerFragmentComponent {

    void inject(EyepetizerFragment eyepetizerFragment);

}
