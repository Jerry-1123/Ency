package com.xxx.ency.contract;

import com.xxx.ency.base.BasePresenter;
import com.xxx.ency.base.BaseView;

/**
 * Created by xiarh on 2017/11/29.
 */

public interface ImageContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 下载图片
         *
         * @param url
         */
        void download(String url);

        /**
         * 设置壁纸
         *
         * @param url
         */
        void setWallpaper(String url);
    }
}
