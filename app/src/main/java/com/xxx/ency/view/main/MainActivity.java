package com.xxx.ency.view.main;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPActivity;
import com.xxx.ency.config.EnycApplication;
import com.xxx.ency.contract.MainContract;
import com.xxx.ency.di.component.DaggerActivityComponent;
import com.xxx.ency.di.module.MainActivityModule;
import com.xxx.ency.model.bean.UpdateBean;
import com.xxx.ency.model.bean.WeatherBean;
import com.xxx.ency.presenter.MainPresenter;
import com.xxx.ency.util.AppExitUtil;

import butterknife.BindView;

/**
 * 主页
 */
public class MainActivity extends BaseMVPActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawerlayout)
    DrawerLayout mDrawerLayout;

    private static final int PERMISSION_CODE = 1000;

    // 权限获取提示框
    private MaterialDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        DaggerActivityComponent
                .builder()
                .appComponent(EnycApplication.getAppComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initialize() {
        mToolbar.setNavigationIcon(R.drawable.ic_slide);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("主页");
        dialog = new MaterialDialog.Builder(mContext)
                .title(R.string.permission_application)
                .content(R.string.permission_application_content)
                .cancelable(false)
                .positiveText(R.string.setting)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivityForResult(intent, PERMISSION_CODE);
                    }
                })
                .negativeText(R.string.no)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // 不给权限就直接退出，不多BB
                        AppExitUtil.exitAPP(mContext);
                    }
                })
                .build();
        mPresenter.checkPermissions();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return AppExitUtil.exitApp(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 检查更新提示框
     */
    @Override
    public void showUpdateDialog(UpdateBean updateBean) {
        new MaterialDialog.Builder(mContext)
                .title(R.string.app_update)
                .content(updateBean.getChangelog())
                .positiveText(R.string.update)
                .positiveColorRes(R.color.black)
                .negativeText(R.string.no)
                .negativeColorRes(R.color.black)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        showMsg("开始更新啦~");
                        startService(new Intent(mContext, UpdateService.class));
                    }
                })
                .show();
    }

    /**
     * 获取天气信息成功
     *
     * @param weatherBean
     */
    @Override
    public void showWeather(WeatherBean weatherBean) {
        showMsg("获取天气信息成功");
    }

    /**
     * 权限未获取，显示提示框
     */
    @Override
    public void showPermissionDialog() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 获得权限成功，进行之后的所有操作
     */
    @Override
    public void getPermissionSuccess() {
        mPresenter.checkUpdate();
        mPresenter.getWeather();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_CODE) {
            mPresenter.checkPermissions();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}