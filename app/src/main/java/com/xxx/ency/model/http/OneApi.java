package com.xxx.ency.model.http;

import com.xxx.ency.model.bean.OneBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 每日一文
 * Created by xiarh on 2017/11/1.
 */

public interface OneApi {

    String HOST = "https://interface.meiriyiwen.com/";

    /**
     * 获取当天
     *
     * @param dev
     * @return
     */
    @GET("article/today")
    Flowable<OneBean> getWeather(@Query("dev") String dev);
}
