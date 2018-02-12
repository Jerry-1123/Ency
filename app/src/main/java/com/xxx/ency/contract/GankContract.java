package com.xxx.ency.contract;

import com.xxx.ency.base.BasePresenter;
import com.xxx.ency.base.BaseView;
import com.xxx.ency.model.bean.GankBean;

/**
 * Created by xiarh on 2017/11/27.
 */

public interface GankContract {

    interface View extends BaseView {

        /**
         * 成功获取数据
         *
         * @param gankBean
         */
        void showGankData(GankBean gankBean);

        /**
         * 获取数据失败
         */
        void failGetData();

        /**
         * 刷新Adapter
         */
        void refreshAdapter(boolean isRefresh);
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取干货数据
         *
         * @param type
         * @param size
         * @param page
         */
        void getGankData(String type, int size, int page);

        /**
         * 省流量模式
         */
        void getPTP();
    }
}
