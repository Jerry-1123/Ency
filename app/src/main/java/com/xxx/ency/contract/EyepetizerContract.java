package com.xxx.ency.contract;

import com.xxx.ency.base.BasePresenter;
import com.xxx.ency.base.BaseView;
import com.xxx.ency.model.bean.DailyVideoBean;

/**
 * Created by xiarh on 2018/2/7.
 */

public interface EyepetizerContract {

    interface View extends BaseView {
        /**
         * 成功获取视频数据
         *
         * @param dailyBean
         */
        void showDailyVideoData(DailyVideoBean dailyBean);

        /**
         * 获取数据失败
         */
        void failGetData();
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取视频
         *
         * @param page
         * @param udid
         */
        void getDailyVideo(int page, String udid);
    }
}
