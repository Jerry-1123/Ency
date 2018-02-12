package com.xxx.ency.view.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPActivity;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.contract.ImageContract;
import com.xxx.ency.di.component.DaggerImageActivityComponent;
import com.xxx.ency.di.module.ImageActivityModule;
import com.xxx.ency.presenter.ImagePresenter;
import com.xxx.ency.util.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 图片详情界面
 * Created by xiarh on 2017/11/28.
 */

public class ImageActivity extends BaseMVPActivity<ImagePresenter> implements ImageContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_preview)
    ImageView imageView;

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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        imgUrl = bundle.getString("imgurl");
        ImageLoader.loadAllAsBitmap(mContext, imgUrl, imageView);
        hideOrShowToolbar();
    }

    @OnClick(R.id.img_preview)
    public void onImgClick() {
        hideOrShowToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_img, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享图片");
                intent.putExtra(Intent.EXTRA_TEXT, imgUrl);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(Intent.createChooser(intent, "分享图片"));
                break;
            case R.id.item_save:
                mPresenter.download(imgUrl);
                break;
            case R.id.item_wallpaper:
                mPresenter.setWallpaper(imgUrl);
                break;
            case android.R.id.home:
                // 回退
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideOrShowToolbar() {
        if (getSupportActionBar().isShowing()) {
            getSupportActionBar().hide();
            hideSystemUI();
        } else {
            showSystemUI();
            getSupportActionBar().show();
        }
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
