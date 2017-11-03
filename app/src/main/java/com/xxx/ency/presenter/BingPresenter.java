package com.xxx.ency.presenter;

import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.contract.BingContract;
import com.xxx.ency.model.bean.BingBean;
import com.xxx.ency.model.http.BingApi;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by xiarh on 2017/11/3.
 */

public class BingPresenter extends RxPresenter<BingContract.View> implements BingContract.Presenter {

    private BingApi bingApi;

    @Inject
    public BingPresenter(BingApi bingApi) {
        this.bingApi = bingApi;
    }

    @Override
    public void getData() {
        addSubscribe(bingApi.getBingBean("800", "600", "json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<BingBean>() {
                    @Override
                    public void onNext(BingBean bingBean) {
                        mView.showBingBean(bingBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.showError(t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
