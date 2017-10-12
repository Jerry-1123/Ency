package com.xxx.ency.contract;

import com.xxx.ency.base.BasePresenter;
import com.xxx.ency.base.BaseView;
import com.xxx.ency.model.bean.WeatherBean;

/**
 * Created by xiarh on 2017/9/25.
 */

public interface MainContract {

    interface View extends BaseView {

        /**
         * 更新提示框
         */
        void showUpdateDialog();

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
         * 未获取权限，直接exit
         */
        void exit();

        /**
         * 获取权限成功
         */
        void getPermissionSuccess();
    }

    interface Presenter extends BasePresenter<View> {

        void checkUpdate();

        void checkPermissions();

        void getWeather();
    }
}
