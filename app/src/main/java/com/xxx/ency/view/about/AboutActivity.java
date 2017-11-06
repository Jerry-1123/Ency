package com.xxx.ency.view.about;

import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPActivity;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.contract.BingContract;
import com.xxx.ency.di.component.DaggerAboutActivityComponent;
import com.xxx.ency.di.module.AboutActivityModule;
import com.xxx.ency.model.bean.BingBean;
import com.xxx.ency.presenter.BingPresenter;

import butterknife.BindView;

/**
 * 关于界面
 * Created by xiarh on 2017/11/3.
 */

public class AboutActivity extends BaseMVPActivity<BingPresenter> implements BingContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.img_about)
    ImageView mImgAbout;

    private RequestOptions options = new RequestOptions()
            .centerCrop()
            .priority(Priority.IMMEDIATE)
            .diskCacheStrategy(DiskCacheStrategy.NONE);

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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("关于");
        mPresenter.getData();
    }

    @Override
    public void showBingBean(BingBean bingBean) {
        Glide.with(mContext)
                .applyDefaultRequestOptions(options)
                .load(bingBean.getData().getBmiddle_pic())
                .into(mImgAbout);
    }
}
