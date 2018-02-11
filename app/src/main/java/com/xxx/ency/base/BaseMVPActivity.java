package com.xxx.ency.base;

import android.view.ViewGroup;

import com.xxx.ency.util.SnackBarUtils;

import javax.inject.Inject;

/**
 * 带MVP和Dagger2的Activity基类
 * Created by xiarh on 2017/9/22.
 */

public abstract class BaseMVPActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    @Inject
    protected T mPresenter;

    protected abstract void initInject();

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showMsg(CharSequence msg) {
        SnackBarUtils.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), msg);
    }

    @Override
    public void showError(CharSequence error) {
        SnackBarUtils.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), error);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void startLoading() {

    }

    @Override
    public void stopLoading() {

    }
}
