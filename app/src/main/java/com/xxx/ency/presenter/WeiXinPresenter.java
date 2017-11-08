package com.xxx.ency.presenter;

import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.config.Constants;
import com.xxx.ency.contract.WeiXinContract;
import com.xxx.ency.model.bean.WeiXinBean;
import com.xxx.ency.model.http.WeiXinApi;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by xiarh on 2017/11/8.
 */

public class WeiXinPresenter extends RxPresenter<WeiXinContract.View> implements WeiXinContract.Presenter {

    private WeiXinApi weiXinApi;

    @Inject
    public WeiXinPresenter(WeiXinApi weiXinApi) {
        this.weiXinApi = weiXinApi;
    }

    @Override
    public void getWeiXinData(int pagesize,int page) {
        addSubscribe(weiXinApi.getWeiXin(Constants.WEIXIN_KEY, pagesize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<WeiXinBean>() {
                    @Override
                    public void onNext(WeiXinBean weiXinBean) {
                        mView.showWeiXinData(weiXinBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.failGetData();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
