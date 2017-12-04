package com.xxx.ency.model.http;

import com.xxx.ency.model.bean.ZhiHuBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * 知乎日报
 * Created by xiarh on 2017/12/4.
 */

public interface ZhiHuApi {

    String HOST = "https://news-at.zhihu.com/";

    /**
     * 最新消息最新消息
     *
     * @return
     */
    @GET("api/4/news/latest")
    Flowable<ZhiHuBean> getZhiHu();
}
