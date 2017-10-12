package com.xxx.ency.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 基于Rx的Presenter封装,控制订阅的生命周期
 * Created by xiarh on 2017/9/25.
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;

    protected CompositeDisposable mCompositeDisposable;

    /**
     * 订阅事件
     *
     * @param
     */
    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 取消所有订阅
     */
    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * 绑定
     *
     * @param view
     */
    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    /**
     * 解绑
     */
    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
