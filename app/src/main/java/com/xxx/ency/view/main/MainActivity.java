package com.xxx.ency.view.main;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPActivity;
import com.xxx.ency.config.EnycApplication;
import com.xxx.ency.contract.MainContract;
import com.xxx.ency.di.component.DaggerActivityComponent;
import com.xxx.ency.di.module.MainActivityModule;
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
    @BindView(R.id.tv_title)
    TextView mTxtTitle;
    @BindView(R.id.menu_slide)
    FrameLayout mSlide;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawerlayout)
    DrawerLayout mDrawerLayout;

    // 判断是否需要获取权限
    private boolean checkPermission = false;

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
        setSupportActionBar(mToolbar);
        mTxtTitle.setText("主页");
        mSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
//        mPresenter.getWeather();
        mPresenter.checkPermissions();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return AppExitUtil.exitApp(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showUpdateDialog() {

    }

    @Override
    public void showWeather(WeatherBean weatherBean) {

    }

    @Override
    public void showPermissionDialog() {

    }

    @Override
    public void exit() {
        Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getPermissionSuccess() {
        Toast.makeText(mContext, "获取成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPermission) {
            mPresenter.checkPermissions();
            checkPermission = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        checkPermission = true;
    }
}
