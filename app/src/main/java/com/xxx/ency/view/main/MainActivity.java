package com.xxx.ency.view.main;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
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
public class MainActivity extends BaseMVPActivity<MainPresenter> implements MainContract.View, AMapLocationListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawerlayout)
    DrawerLayout mDrawerLayout;

    private View mHeaderView;

    private TextView mTxtCity;

    private ImageView mImgWeather;

    private TextView mTxtWeather;

    private TextView mTextTemperature;

    private static final int PERMISSION_CODE = 1000;

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;

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
        mHeaderView = mNavView.getHeaderView(0);
        mTxtCity = mHeaderView.findViewById(R.id.txt_city);
        mImgWeather = mHeaderView.findViewById(R.id.img_weather);
        mTxtWeather = mHeaderView.findViewById(R.id.txt_weather);
        mTextTemperature = mHeaderView.findViewById(R.id.txt_temperature);
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

    /**
     * 定位初始化
     */
    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(mContext);
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔 单位毫秒
        mLocationOption.setInterval(100 * 1000 * 60 * 60);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //启动定位
        mLocationClient.startLocation();
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
        if (null != updateBean) {
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
                            showMsg("开始更新啦~");
                            startService(new Intent(mContext, UpdateService.class));
                        }
                    })
                    .show();
        }
    }

    /**
     * 获取天气信息成功
     *
     * @param weatherBean
     */
    @Override
    public void showWeather(WeatherBean weatherBean) {
        mTxtCity.setText(weatherBean.getHeWeather5().get(0).getBasic().getCity());
        mImgWeather.setBackgroundResource(switchWeather(weatherBean.getHeWeather5().get(0).getNow().getCond().getTxt()));
        mTxtWeather.setText(weatherBean.getHeWeather5().get(0).getNow().getCond().getTxt());
        mTextTemperature.setText(weatherBean.getHeWeather5().get(0).getNow().getTmp() + "°C");
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
        initLocation();
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                Log.e("xrh", aMapLocation.getCity());
                mPresenter.getWeather(aMapLocation.getCity());
            }
            //定位失败，通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息
            else {
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 获取天气图标
     *
     * @return
     */
    private int switchWeather(String txt) {
        if (txt.contains("晴")) {
            return R.drawable.ic_sunny;
        } else if (txt.contains("云")) {
            return R.drawable.ic_cloudy;
        } else if (txt.contains("风")) {
            return R.drawable.ic_wind;
        } else if (txt.contains("雨")) {
            return R.drawable.ic_rainy;
        } else if (txt.contains("雪")) {
            return R.drawable.ic_snowy;
        } else if (txt.contains("阴")) {
            return R.drawable.ic_overcast;
        } else if (txt.contains("霾")) {
            return R.drawable.ic_haze;
        } else {
            return R.drawable.ic_unknown;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 销毁定位
         * 如果AMapLocationClient是在当前Activity实例化的，
         * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
         */
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
        }
    }
}
