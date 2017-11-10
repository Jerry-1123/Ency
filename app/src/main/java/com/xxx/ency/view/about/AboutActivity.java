package com.xxx.ency.view.about;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPActivity;
import com.xxx.ency.config.Constants;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.config.GlideApp;
import com.xxx.ency.contract.AboutContract;
import com.xxx.ency.di.component.DaggerAboutActivityComponent;
import com.xxx.ency.di.module.AboutActivityModule;
import com.xxx.ency.model.bean.BingBean;
import com.xxx.ency.presenter.AboutPresenter;
import com.xxx.ency.util.AppApplicationUtil;
import com.xxx.ency.util.WebUtil;
import com.xxx.ency.view.main.UpdateService;
import com.xxx.ency.view.web.WebActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于界面
 * Created by xiarh on 2017/11/3.
 */

public class AboutActivity extends BaseMVPActivity<AboutPresenter> implements AboutContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.img_about)
    ImageView mImgAbout;
    @BindView(R.id.txt_version)
    TextView txtVersion;

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
        txtVersion.setText(getResources().getString(R.string.version) + AppApplicationUtil.getVersionName(mContext));
        mPresenter.getBingData();
    }

    @Override
    public void showBingBean(BingBean bingBean) {
        GlideApp.with(mContext)
                .load(bingBean.getData().getOriginal_pic())
                .centerCrop()
                .error(R.drawable.bg_about)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mImgAbout);
    }

    @Override
    public void startUpdate() {
        startService(new Intent(mContext, UpdateService.class));
    }

    @OnClick(R.id.txt_github)
    public void onTxtGithubClicked() {
        WebUtil.openUrl(mContext, "", "", Constants.TYPE_DEFAULT
                , "https://github.com/xiarunhao123/Ency", "项目主页", false);
    }

    @OnClick(R.id.txt_email)
    public void onTxtEmailClicked() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "xiarunhao123@163.com", null));
        intent.putExtra(Intent.EXTRA_EMAIL, "xiarunhao123@163.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "意见反馈");
        startActivity(Intent.createChooser(intent, "意见反馈"));
    }

    @OnClick(R.id.txt_update)
    public void onTxtUpdateClicked() {
        mPresenter.getUpdateData();
    }

    @OnClick(R.id.txt_share)
    public void onTxtShareClicked() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "http://fir.im/Ency");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(Intent.createChooser(intent, "分享"));
    }
}