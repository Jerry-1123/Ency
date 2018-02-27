package com.xxx.ency.view.main;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
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
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.contract.MainContract;
import com.xxx.ency.di.component.DaggerMainActivityComponent;
import com.xxx.ency.di.module.MainActivityModule;
import com.xxx.ency.model.bean.UpdateBean;
import com.xxx.ency.model.bean.WeatherBean;
import com.xxx.ency.model.prefs.SharePrefManager;
import com.xxx.ency.presenter.MainPresenter;
import com.xxx.ency.util.AppExitUtil;
import com.xxx.ency.util.ImageLoader;
import com.xxx.ency.util.LogUtil;
import com.xxx.ency.util.SystemUtil;
import com.xxx.ency.util.WeatherUtil;
import com.xxx.ency.view.about.AboutActivity;
import com.xxx.ency.view.eyepetizer.EyepetizerFragment;
import com.xxx.ency.view.gank.GankMainFragment;
import com.xxx.ency.view.like.LikeFragment;
import com.xxx.ency.view.one.OneFragment;
import com.xxx.ency.view.setting.SettingActivity;
import com.xxx.ency.view.weixin.WeiXinFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 主页
 */
public class MainActivity extends BaseMVPActivity<MainPresenter> implements MainContract.View, AMapLocationListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawerlayout)
    DrawerLayout mDrawerLayout;

    @Inject
    SharePrefManager sharePrefManager;

    private View mHeaderView;

    private TextView mTxtCity;

    private TextView mTxtWeather;

    private ImageView mImgWeather;

    private TextView mTextTemperature;

    private ImageView mImgWeatherBg;

    private static final int PERMISSION_CODE = 1000;

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;

    // 权限获取提示框
    private MaterialDialog dialog;

    private WeiXinFragment weiXinFragment;
    private OneFragment oneFragment;
    private LikeFragment likeFragment;
    private EyepetizerFragment eyepetizerFragment;
    private GankMainFragment gankFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        DaggerMainActivityComponent
                .builder()
                .appComponent(EncyApplication.getAppComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initialize() {
        setSupportActionBar(mToolbar);
        setTitle("微信精选");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        WeatherUtil.init(mContext);
        mHeaderView = mNavView.getHeaderView(0);
        mTxtCity = mHeaderView.findViewById(R.id.txt_city);
        mTxtWeather = mHeaderView.findViewById(R.id.txt_weather);
        mImgWeather = mHeaderView.findViewById(R.id.img_weather);
        mTextTemperature = mHeaderView.findViewById(R.id.txt_temperature);
        mImgWeatherBg = mHeaderView.findViewById(R.id.img_weather_bg);
        if (sharePrefManager.getNightMode()) {
            ImageLoader.loadAll(mContext, R.drawable.bg_weather_night, mImgWeatherBg);
        } else {
            ImageLoader.loadAll(mContext, R.drawable.bg_weather_day, mImgWeatherBg);
        }
        mPresenter.checkPermissions();
        mPresenter.setDayOrNight();
        initDialog();
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

    private void initDialog() {
        dialog = new MaterialDialog.Builder(mContext)
                .title(R.string.permission_application)
                .content(R.string.permission_application_content)
                .cancelable(false)
                .positiveText(R.string.setting)
                .positiveColorRes(R.color.colorPositive)
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
                .negativeColorRes(R.color.colorNegative)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // 不给权限就直接退出
                        AppExitUtil.exitAPP(mContext);
                    }
                })
                .build();
    }

    private void initFragment() {
        weiXinFragment = new WeiXinFragment();
        oneFragment = new OneFragment();
        likeFragment = new LikeFragment();
        eyepetizerFragment = new EyepetizerFragment();
        gankFragment = new GankMainFragment();
        loadMultipleRootFragment(R.id.main_content, 0, weiXinFragment, oneFragment, gankFragment, eyepetizerFragment, likeFragment);
    }

    /**
     * 检查更新提示框
     */
    @Override
    public void showUpdateDialog(final UpdateBean updateBean) {
        if (null != updateBean) {
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
    }

    /**
     * 获取天气信息成功
     *
     * @param weatherBean
     */
    @Override
    public void showWeather(WeatherBean weatherBean) {
        mTxtCity.setText(weatherBean.getHeWeather6().get(0).getBasic().getLocation());
        mTxtWeather.setText(weatherBean.getHeWeather6().get(0).getNow().getCond_txt() + " " + weatherBean.getHeWeather6().get(0).getNow().getWind_dir());
        mTextTemperature.setText(weatherBean.getHeWeather6().get(0).getNow().getTmp() + "°");
        ImageLoader.loadAllAsBitmap(mContext, WeatherUtil.getImageUrl(weatherBean.getHeWeather6().get(0).getNow().getCond_code()), mImgWeather);
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
        initFragment();
        //设置选中
        mNavView.getMenu().getItem(0).setChecked(true);
        mNavView.setNavigationItemSelectedListener(this);
        mPresenter.checkUpdate();
        initLocation();
    }

    @Override
    public void changeDayOrNight(boolean changed) {
        if (changed) {
            finish();
        }
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_weixin:
                setTitle("微信精选");
                showHideFragment(weiXinFragment);
                break;
            case R.id.item_one:
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                setTitle(formatter.format(new Date()));
                showHideFragment(oneFragment);
                break;
            case R.id.item_gank:
                setTitle("干货热门");
                showHideFragment(gankFragment);
                break;
            case R.id.item_eyepetizer:
                setTitle("开眼视频");
                showHideFragment(eyepetizerFragment);
                break;
            case R.id.item_like:
                setTitle("我的收藏");
                showHideFragment(likeFragment);
                break;
            case R.id.item_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.item_about:
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mPresenter.getWeather(aMapLocation.getCity());
            }
            //定位失败，通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息
            else {
                LogUtil.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            return AppExitUtil.exitApp(this, mToolbar);
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onBackPressedSupport() {
        AppExitUtil.exitApp(this, mToolbar);
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