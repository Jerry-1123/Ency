package com.xxx.ency.base;

/**
 * View基类
 * Created by xiarh on 2017/9/22.
 */

public interface BaseView {

    void showMsg(String msg);

    void showError(String error);

    void showEmptyView();

    void startLoading();

    void stopLoading();
}
