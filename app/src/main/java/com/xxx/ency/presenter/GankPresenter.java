package com.xxx.ency.presenter;

import android.content.Context;

import com.xxx.ency.base.BaseSubscriber;
import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.contract.GankContract;
import com.xxx.ency.model.bean.GankBean;
import com.xxx.ency.model.http.GankApi;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiarh on 2017/11/27.
 */

public class GankPresenter extends RxPresenter<GankContract.View> implements GankContract.Presenter {

    private GankApi gankApi;

    private Context context;

    @Inject
    public GankPresenter(GankApi gankApi, Context context) {
        this.gankApi = gankApi;
        this.context = context;
    }

    @Override
    public void getGankData(String type, int size, int page) {
        addSubscribe(gankApi.getGanHuoDatas(type, size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<GankBean>(context, mView) {
                    @Override
                    public void onNext(GankBean gankBean) {
                        mView.showGankData(gankBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mView.failGetData();
                    }
                }));
    }
}
