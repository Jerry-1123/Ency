package com.xxx.ency.presenter;

import android.Manifest;
import android.content.Context;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xxx.ency.R;
import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.config.Constants;
import com.xxx.ency.contract.MainContract;
import com.xxx.ency.model.bean.UpdateBean;
import com.xxx.ency.model.bean.WeatherBean;
import com.xxx.ency.model.http.UpdateApi;
import com.xxx.ency.model.http.WeatherApi;
import com.xxx.ency.util.AppApplicationUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by xiarh on 2017/9/25.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private WeatherApi weatherApi;

    private UpdateApi updateApi;

    private RxPermissions rxPermissions;

    private Context context;

    @Inject
    public MainPresenter(WeatherApi weatherApi, UpdateApi updateApi, RxPermissions rxPermissions, Context context) {
        this.weatherApi = weatherApi;
        this.updateApi = updateApi;
        this.rxPermissions = rxPermissions;
        this.context = context;
    }

    @Override
    public void checkUpdate() {
        addSubscribe(updateApi.getVersionInfo(Constants.FIR_IM_API_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<UpdateBean>() {
                    @Override
                    public void onNext(UpdateBean updateBean) {
//                        if (AppApplicationUtil.getVersionCode(context) < updateBean.getVersion()) {
                            mView.showUpdateDialog(updateBean);
//                        } else if (AppApplicationUtil.getVersionCode(context) == updateBean.getVersion()) {
//                            mView.showMsg(context.getResources().getString(R.string.update_msg));
//                        }
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

    @Override
    public void checkPermissions() {
        addSubscribe(rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        // 当所有权限都允许之后，返回true
                        if (aBoolean) {
                            mView.getPermissionSuccess();
                        }
                        // 只要有一个权限禁止，返回false，
                        // 下一次申请只申请没通过申请的权限
                        else {
                            mView.showPermissionDialog();
                        }
                    }
                }));
    }

    @Override
    public void getWeather() {
        addSubscribe(weatherApi.getWeather("苏州", Constants.WEATHER_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<WeatherBean>() {
                    @Override
                    public void onNext(WeatherBean weatherBean) {
                        mView.showWeather(weatherBean);
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