package com.xxx.ency.presenter;

import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.config.Constants;
import com.xxx.ency.contract.MainContract;
import com.xxx.ency.model.bean.WeatherBean;
import com.xxx.ency.model.http.api.WeatherApi;
import com.xxx.ency.util.LogUtil;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiarh on 2017/9/25.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private WeatherApi api;

    @Inject
    public MainPresenter(WeatherApi api) {
        this.api = api;
    }

    @Override
    public void checkUpdate() {

    }

    @Override
    public void checkPermissions() {

    }

    @Override
    public void getWeather() {
        api.getWeather("苏州", Constants.WEATHER_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull WeatherBean weatherBean) {
                        LogUtil.i(weatherBean.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.i(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
