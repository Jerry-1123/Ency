package com.xxx.ency.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.xxx.ency.util.SnackBarUtils;

import javax.inject.Inject;

/**
 * 带MVP和Dagger2的Activity基类
 * Created by xiarh on 2017/9/22.
 */

public abstract class BaseMVPFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    @Inject
    protected T mPresenter;

    protected abstract void initInject();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public void showMsg(CharSequence msg) {
        SnackBarUtils.show(((ViewGroup) mActivity.findViewById(android.R.id.content)).getChildAt(0), msg);
    }

    @Override
    public void showError(CharSequence error) {
        SnackBarUtils.show(((ViewGroup) mActivity.findViewById(android.R.id.content)).getChildAt(0), error);
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
