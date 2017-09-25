package com.xxx.ency.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 带MVP和Dagger2的Activity基类
 * Created by xiarh on 2017/9/22.
 */

public abstract class BaseMVPFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    protected T mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
