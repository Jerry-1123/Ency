package com.xxx.ency.presenter;

import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.contract.OneContract;

import javax.inject.Inject;

/**
 * Created by xiarh on 2017/11/1.
 */

public class OnePresenter extends RxPresenter<OneContract.View> implements OneContract.Presenter {

    @Inject
    public OnePresenter() {

    }

    @Override
    public void getData(String url) {

    }
}
