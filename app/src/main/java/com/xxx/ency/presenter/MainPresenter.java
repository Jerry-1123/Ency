package com.xxx.ency.presenter;

import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.contract.MainContract;
import com.xxx.ency.model.http.api.WeatherApi;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by xiarh on 2017/9/25.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    @Override
    public void checkUpdate() {

    }

    @Override
    public void checkPermissions() {

    }

    @Override
    public void getWeather() {

    }
}
