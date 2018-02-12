package com.xxx.ency.presenter;

import android.content.Context;

import com.xxx.ency.base.BaseSubscriber;
import com.xxx.ency.base.RxBus;
import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.config.Constants;
import com.xxx.ency.contract.WeiXinContract;
import com.xxx.ency.model.bean.WeiXinBean;
import com.xxx.ency.model.http.WeiXinApi;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiarh on 2017/11/8.
 */

public class WeiXinPresenter extends RxPresenter<WeiXinContract.View> implements WeiXinContract.Presenter {

    private WeiXinApi weiXinApi;

    private Context context;

    @Inject
    public WeiXinPresenter(WeiXinApi weiXinApi,Context context) {
        this.weiXinApi = weiXinApi;
        this.context = context;
    }

    @Override
    public void getWeiXinData(int pagesize,int page) {
        addSubscribe(weiXinApi.getWeiXin(Constants.WEIXIN_KEY, pagesize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<WeiXinBean>(context, mView) {
                    @Override
                    public void onNext(WeiXinBean weiXinBean) {
                        mView.showWeiXinData(weiXinBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mView.failGetData();
                    }
                }));
    }

    @Override
    public void getPTP() {
        addSubscribe(RxBus.getInstance().register(Integer.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<Integer>(context, mView) {
                    @Override
                    public void onNext(Integer integer) {
                        if (integer == 1001) {
                            mView.refreshAdapter(true);
                        }
                    }
                }));
    }
}
