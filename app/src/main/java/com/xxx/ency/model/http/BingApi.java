package com.xxx.ency.model.http;

import com.xxx.ency.model.bean.BingBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Bing壁纸
 * Created by xiarh on 2017/11/3.
 */

public interface BingApi {

    String HOST = "https://bing.ioliu.cn/";

    /**
     * Bing随机壁纸
     *
     * @param width
     * @param height
     * @param type
     * @return
     */
    @GET("v1/rand")
    Flowable<BingBean> getBingBean(@Query("w") String width, @Query("h") String height, @Query("type") String type);
}
