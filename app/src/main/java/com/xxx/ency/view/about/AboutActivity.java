package com.xxx.ency.view.about;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPActivity;
import com.xxx.ency.config.Constants;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.contract.AboutContract;
import com.xxx.ency.di.component.DaggerAboutActivityComponent;
import com.xxx.ency.di.module.AboutActivityModule;
import com.xxx.ency.model.bean.BingBean;
import com.xxx.ency.model.bean.UpdateBean;
import com.xxx.ency.model.prefs.SharePrefManager;
import com.xxx.ency.presenter.AboutPresenter;
import com.xxx.ency.util.AppApplicationUtil;
import com.xxx.ency.util.ImageLoader;
import com.xxx.ency.util.SystemUtil;
import com.xxx.ency.view.main.UpdateService;
import com.xxx.ency.view.web.WebActivity;

import javax.inject.Inject;

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

    @Inject
    SharePrefManager sharePrefManager;

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
        if (sharePrefManager.getNightMode()) {
            ImageLoader.loadAllNoPlaceHolder(mContext, bingBean.getData().getOriginal_pic(), R.drawable.bg_about_night, mImgAbout);
        } else {
            ImageLoader.loadAllNoPlaceHolder(mContext, bingBean.getData().getOriginal_pic(), R.drawable.bg_about_day, mImgAbout);
        }
    }

    @Override
    public void showUpdateDialog(final UpdateBean updateBean) {
        new MaterialDialog.Builder(mContext)
                .title(R.string.app_update)
                .content("最新版本：" + updateBean.getVersionShort() + "\n"
                        + "版本大小：" + SystemUtil.getFormatSize(updateBean.getBinary().getFsize()) + "\n"
                        + "更新内容：" + updateBean.getChangelog())
                .negativeText(R.string.no)
                .negativeColorRes(R.color.colorNegative)
                .positiveText(R.string.update)
                .positiveColorRes(R.color.colorPositive)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showMsg(getResources().getString(R.string.start_update));
                        Intent intent = new Intent(mContext, UpdateService.class);
                        intent.putExtra("downloadurl", updateBean.getInstall_url());
                        startService(intent);
                    }
                })
                .show();
    }

    @OnClick(R.id.txt_github)
    public void onTxtGithubClicked() {
        WebActivity.open(new WebActivity.Builder()
                .setGuid("")
                .setImgUrl("")
                .setType(Constants.TYPE_DEFAULT)
                .setUrl("https://github.com/xiarunhao123/Ency")
                .setTitle("项目主页")
                .setShowLikeIcon(false)
                .setContext(mContext)
        );
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