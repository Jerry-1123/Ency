package com.xxx.ency.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 带MVP和Dagger2的Activity基类
 * Created by xiarh on 2017/9/22.
 */

public abstract class BaseMVPActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();
    }
}
