package com.xxx.ency.contract;

import com.xxx.ency.base.BasePresenter;
import com.xxx.ency.base.BaseView;
import com.xxx.ency.model.bean.ZhiHuBean;

/**
 * Created by xiarh on 2017/12/4.
 */

public interface ZhiHuContract {

    interface View extends BaseView {

        /**
         * 成功获取数据
         *
         * @param zhiHuBean
         */
        void showZhiHuData(ZhiHuBean zhiHuBean);

        /**
         * 获取数据失败
         */
        void failGetData();
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取知乎日报
         */
        void getZhiHuData();
    }
}
