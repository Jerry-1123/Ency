package com.xxx.ency.base;

/**
 * View基类
 * Created by xiarh on 2017/9/22.
 */

public interface BaseView {

    void showMsg(CharSequence msg);

    void showError(CharSequence error);

    void showEmptyView();

    void startLoading();

    void stopLoading();
}
