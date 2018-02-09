package com.xxx.ency.presenter;

import android.content.Context;

import com.xxx.ency.base.BaseSubscriber;
import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.contract.EyepetizerContract;
import com.xxx.ency.model.bean.VideoBean;
import com.xxx.ency.model.http.EyepetizerApi;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiarh on 2018/2/7.
 */

public class EyepetizerPresenter extends RxPresenter<EyepetizerContract.View> implements EyepetizerContract.Presenter {

    private EyepetizerApi eyepetizerApi;

    private Context context;

    @Inject
    public EyepetizerPresenter(EyepetizerApi eyepetizerApi, Context context) {
        this.eyepetizerApi = eyepetizerApi;
        this.context = context;
    }

    @Override
    public void getDailyVideo(int page, String udid) {
        addSubscribe(eyepetizerApi.getDailyVideo(page, udid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<VideoBean>(context, null) {
                    @Override
                    public void onNext(VideoBean dailyVideoBean) {
                        if (dailyVideoBean.isAdExist()) {
                            mView.showDailyVideoData(dailyVideoBean);
                        } else {
                            mView.failGetDailyData();
                        }
                    }
                }));
    }

    @Override
    public void getHotVideo(String strategy, String vc, String deviceModel) {
        addSubscribe(eyepetizerApi.getHotVideo(strategy,vc,deviceModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<VideoBean>(context, null) {
                    @Override
                    public void onNext(VideoBean hotVideoBean) {
                        if (hotVideoBean != null) {
                            mView.showHotVideoData(hotVideoBean);
                        } else {
                            mView.failGetHotData();
                        }
                    }
                }));
    }
}
