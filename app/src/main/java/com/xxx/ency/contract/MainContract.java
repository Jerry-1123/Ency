package com.xxx.ency.contract;

import com.xxx.ency.base.BasePresenter;
import com.xxx.ency.base.BaseView;

/**
 * Created by xiarh on 2017/9/25.
 */

public interface MainContract {

    interface View extends BaseView {

        void showUpdateDialog();

        void showWeather();
    }

    interface Presenter extends BasePresenter<View> {

        void checkUpdate();

        void checkPermissions();

        void getWeather();
    }
}
