package com.xxx.ency.base;

/**
 * Presenter基类
 * Created by xiarh on 2017/9/22.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();
}
