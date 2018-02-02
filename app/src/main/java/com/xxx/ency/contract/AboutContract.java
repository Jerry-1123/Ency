package com.xxx.ency.contract;

import com.xxx.ency.base.BasePresenter;
import com.xxx.ency.base.BaseView;
import com.xxx.ency.model.bean.BingBean;
import com.xxx.ency.model.bean.UpdateBean;

/**
 * Created by xiarh on 2017/11/3.
 */

public interface AboutContract {

    interface View extends BaseView {

        /**
         * 展示壁纸数据
         *
         * @param bingBean
         */
        void showBingBean(BingBean bingBean);

        /**
         * 更新
         */
        void showUpdateDialog(UpdateBean updateBean);
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取壁纸数据
         */
        void getBingData();

        /**
         * 获取更新数据
         */
        void getUpdateData();
    }
}
