package com.xxx.ency.presenter;

import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.contract.OneContract;
import com.xxx.ency.model.bean.OneBean;
import com.xxx.ency.model.http.OneApi;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by xiarh on 2017/11/1.
 */

public class OnePresenter extends RxPresenter<OneContract.View> implements OneContract.Presenter {

    private OneApi oneApi;

    @Inject
    public OnePresenter(OneApi oneApi) {
        this.oneApi = oneApi;
    }

    @Override
    public void getData() {
        addSubscribe(oneApi.getWeather("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<OneBean>() {

                    @Override
                    public void onNext(OneBean oneBean) {
                        mView.showOneBean(oneBean);
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
