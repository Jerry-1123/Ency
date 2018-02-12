package com.xxx.ency.contract;

import com.xxx.ency.base.BasePresenter;
import com.xxx.ency.base.BaseView;
import com.xxx.ency.model.bean.WeiXinBean;

/**
 * Created by xiarh on 2017/11/8.
 */

public interface WeiXinContract {

    interface View extends BaseView {

        /**
         * 成功获取数据
         *
         * @param weiXinBean
         */
        void showWeiXinData(WeiXinBean weiXinBean);

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
         * 获取微信数据
         *
         * @param pagesize
         * @param page
         */
        void getWeiXinData(int pagesize, int page);

        /**
         * 省流量模式
         */
        void getPTP();
    }
}
