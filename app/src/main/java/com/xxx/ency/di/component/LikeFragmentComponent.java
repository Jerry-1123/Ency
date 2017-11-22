package com.xxx.ency.di.component;

import com.xxx.ency.di.module.LikeFragmentModule;
import com.xxx.ency.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by xiarh on 2017/11/22.
 */

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = LikeFragmentModule.class )
public interface LikeFragmentComponent {


}
