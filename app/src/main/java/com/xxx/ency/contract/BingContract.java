package com.xxx.ency.contract;

import com.xxx.ency.base.BasePresenter;
import com.xxx.ency.base.BaseView;
import com.xxx.ency.model.bean.BingBean;

/**
 * Created by xiarh on 2017/11/3.
 */

public interface BingContract {

    interface View extends BaseView {

        void showBingBean(BingBean bingBean);
    }

    interface Presenter extends BasePresenter<View> {

        void getData();
    }
}
