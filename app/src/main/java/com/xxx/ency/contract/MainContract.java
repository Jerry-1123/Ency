package com.xxx.ency.contract;

import com.xxx.ency.base.BasePresenter;
import com.xxx.ency.base.BaseView;
import com.xxx.ency.model.bean.UpdateBean;
import com.xxx.ency.model.bean.WeatherBean;

/**
 * Created by xiarh on 2017/9/25.
 */

public interface MainContract {

    interface View extends BaseView {

        /**
         * 更新提示框
         */
        void showUpdateDialog(UpdateBean updateBean);

        /**
         * 天气数据
         *
         * @param weatherBean
         */
        void showWeather(WeatherBean weatherBean);

        /**
         * 未获取权限，弹出提示框
         */
        void showPermissionDialog();

        /**
         * 获取权限成功
         */
        void getPermissionSuccess();

        /**
         * 是否改变
         */
        void changeDayOrNight(boolean changed);
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 检查更新
         */
        void checkUpdate();

        /**
         * 检查权限
         */
        void checkPermissions();

        /**
         * 拉取天气权限
         */
        void getWeather(String location);

        /**
         * 设置白天/夜间模式
         */
        void setDayOrNight();
    }
}
