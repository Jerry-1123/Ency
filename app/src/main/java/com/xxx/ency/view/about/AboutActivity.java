package com.xxx.ency.view.about;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPActivity;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.contract.BingContract;
import com.xxx.ency.di.component.DaggerAboutActivityComponent;
import com.xxx.ency.di.module.AboutActivityModule;
import com.xxx.ency.model.bean.BingBean;
import com.xxx.ency.presenter.BingPresenter;

/**
 * 关于界面
 * Created by xiarh on 2017/11/3.
 */

public class AboutActivity extends BaseMVPActivity<BingPresenter> implements BingContract.View {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initInject() {
        DaggerAboutActivityComponent
                .builder()
                .appComponent(EncyApplication.getAppComponent())
                .aboutActivityModule(new AboutActivityModule())
                .build()
                .inject(this);
    }

    @Override
    protected void initialize() {
        mPresenter.getData();
    }

    @Override
    public void showBingBean(BingBean bingBean) {
        showMsg("success");
    }
}
