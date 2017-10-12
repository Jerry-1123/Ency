package com.xxx.ency.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xxx.ency.R;
import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.config.Constants;
import com.xxx.ency.contract.MainContract;
import com.xxx.ency.model.bean.WeatherBean;
import com.xxx.ency.model.http.api.WeatherApi;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by xiarh on 2017/9/25.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private WeatherApi api;

    private RxPermissions rxPermissions;

    private MaterialDialog dialog;

    @Inject
    public MainPresenter(WeatherApi api, RxPermissions rxPermissions, final Activity activity) {
        this.api = api;
        this.rxPermissions = rxPermissions;
        dialog = new MaterialDialog.Builder(activity)
                .title(R.string.permission_application)
                .content(R.string.permission_application_content)
                .cancelable(false)
                .positiveText(R.string.setting)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                        activity.startActivity(intent);
                    }
                })
                .negativeText(R.string.no)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mView.exit();
                    }
                })
                .build();
    }

    @Override
    public void checkUpdate() {

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
                            if (!dialog.isShowing()) {
                                dialog.show();
                            }
                        }
                    }
                }));
    }

    @Override
    public void getWeather() {
        addSubscribe(api.getWeather("苏州", Constants.WEATHER_KEY)
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