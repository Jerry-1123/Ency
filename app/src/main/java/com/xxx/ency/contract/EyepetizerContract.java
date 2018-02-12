package com.xxx.ency.contract;

import com.xxx.ency.base.BasePresenter;
import com.xxx.ency.base.BaseView;
import com.xxx.ency.model.bean.VideoBean;

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
        void showDailyVideoData(VideoBean dailyBean);

        /**
         * 获取数据失败
         */
        void failGetDailyData();

        /**
         * 成功获取热门视频数据
         *
         * @param hotBean
         */
        void showHotVideoData(VideoBean hotBean);

        /**
         * 获取数据失败
         */
        void failGetHotData();

        /**
         * 刷新Adapter
         */
        void refreshAdapter(boolean isRefresh);
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取视频
         *
         * @param page
         * @param udid
         */
        void getVideoData(int page, String udid, String strategy, String vc, String deviceModel);

        /**
         * 获取视频
         *
         * @param page
         * @param udid
         */
        void getDailyVideoData(int page, String udid);

        /**
         * 省流量模式
         */
        void getPTP();
    }
}
