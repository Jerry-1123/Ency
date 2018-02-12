package com.xxx.ency.presenter;

import android.content.Context;

import com.xxx.ency.base.BaseSubscriber;
import com.xxx.ency.base.RxBus;
import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.contract.EyepetizerContract;
import com.xxx.ency.model.bean.VideoBean;
import com.xxx.ency.model.http.EyepetizerApi;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
    public void getDailyVideoData(int page, String udid) {
        addSubscribe(eyepetizerApi.getDailyVideo(page, udid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<VideoBean>(context, mView) {
                    @Override
                    public void onNext(VideoBean dailyVideoBean) {
                        mView.showDailyVideoData(dailyVideoBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mView.failGetDailyData();
                    }
                }));
    }

    @Override
    public void getVideoData(final int page, final String udid, String strategy, String vc, String deviceModel) {
        addSubscribe(eyepetizerApi.getHotVideo(strategy, vc, deviceModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<VideoBean>() {
                    @Override
                    public void accept(VideoBean hotVideoBean) throws Exception {
                        mView.showHotVideoData(hotVideoBean);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.failGetHotData();
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<VideoBean, Publisher<VideoBean>>() {
                    @Override
                    public Publisher<VideoBean> apply(VideoBean videoBean) throws Exception {
                        return eyepetizerApi.getDailyVideo(page, udid);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<VideoBean>(context, mView) {
                    @Override
                    public void onNext(VideoBean dailyVideoBean) {
                        mView.showDailyVideoData(dailyVideoBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mView.failGetDailyData();
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
