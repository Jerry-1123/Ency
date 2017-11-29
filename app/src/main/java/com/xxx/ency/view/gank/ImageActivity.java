package com.xxx.ency.view.gank;

import android.os.Bundle;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;
import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPActivity;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.config.GlideApp;
import com.xxx.ency.contract.ImageContract;
import com.xxx.ency.di.component.DaggerImageActivityComponent;
import com.xxx.ency.di.module.ImageActivityModule;
import com.xxx.ency.presenter.ImagePresenter;
import com.xxx.ency.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 图片详情界面
 * Created by xiarh on 2017/11/28.
 */

public class ImageActivity extends BaseMVPActivity<ImagePresenter> implements ImageContract.View {

    @BindView(R.id.img_preview)
    PhotoView photoView;

    private String imgUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initInject() {
        DaggerImageActivityComponent
                .builder()
                .appComponent(EncyApplication.getAppComponent())
                .imageActivityModule(new ImageActivityModule())
                .build()
                .inject(this);
    }

    @Override
    protected void initialize() {
        Bundle bundle = getIntent().getExtras();
        imgUrl = bundle.getString("imgurl");
        GlideApp.with(mContext)
                .load(imgUrl)
                .fitCenter()
                .placeholder(R.drawable.ic_ali)
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(photoView);
    }

    @OnClick(R.id.img_download)
    public void download() {
        mPresenter.download(imgUrl);
    }
}
