package com.xxx.ency.contract;

import com.xxx.ency.base.BasePresenter;
import com.xxx.ency.base.BaseView;
import com.xxx.ency.model.bean.OneBean;

/**
 * Created by xiarh on 2017/11/1.
 */

public interface OneContract {

    interface View extends BaseView {

        /**
         * 获取One数据成功
         *
         * @param oneBean
         */
        void showOneBean(OneBean oneBean);

        /**
         * 获取数据失败
         */
        void failGetData();

    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取One数据
         */
        void getData(String url);
    }
}
