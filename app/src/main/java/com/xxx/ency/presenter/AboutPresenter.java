package com.xxx.ency.presenter;

import android.content.Context;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseSubscriber;
import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.config.Constants;
import com.xxx.ency.contract.AboutContract;
import com.xxx.ency.model.bean.BingBean;
import com.xxx.ency.model.bean.UpdateBean;
import com.xxx.ency.model.http.BingApi;
import com.xxx.ency.model.http.UpdateApi;
import com.xxx.ency.util.AppApplicationUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiarh on 2017/11/3.
 */

public class AboutPresenter extends RxPresenter<AboutContract.View> implements AboutContract.Presenter {

    private BingApi bingApi;

    private UpdateApi updateApi;

    private Context context;

    @Inject
    public AboutPresenter(BingApi bingApi, UpdateApi updateApi, Context context) {
        this.bingApi = bingApi;
        this.updateApi = updateApi;
        this.context = context;
    }

    @Override
    public void getBingData() {
        addSubscribe(bingApi.getBingBean("800", "600", "json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BingBean>(context, mView) {
                    @Override
                    public void onNext(BingBean bingBean) {
                        mView.showBingBean(bingBean);
                    }
                }));
    }

    @Override
    public void getUpdateData() {
        addSubscribe(updateApi.getVersionInfo(Constants.FIR_IM_API_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<UpdateBean>(context, mView) {
                    @Override
                    public void onNext(UpdateBean updateBean) {
                        if (null != updateBean) {
                            if (AppApplicationUtil.getVersionCode(context) < updateBean.getVersion()) {
                                mView.showMsg(context.getResources().getString(R.string.start_update));
                                mView.showUpdateDialog(updateBean);
                            } else if (AppApplicationUtil.getVersionCode(context) == updateBean.getVersion()) {
                                mView.showMsg(context.getResources().getString(R.string.update_msg));
                            }
                        }
                    }
                }));
    }
}
