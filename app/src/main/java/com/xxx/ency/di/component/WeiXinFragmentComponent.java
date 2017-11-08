package com.xxx.ency.di.component;

import com.xxx.ency.di.module.WeiXinFragmentModule;
import com.xxx.ency.di.scope.FragmentScope;
import com.xxx.ency.view.weixin.WeiXinFragment;

import dagger.Component;

/**
 * Created by xiarh on 2017/11/8.
 */

@FragmentScope
@Component(dependencies = AppComponent.class,
        modules = WeiXinFragmentModule.class)
public interface WeiXinFragmentComponent {

    void inject(WeiXinFragment weiXinFragment);

}
