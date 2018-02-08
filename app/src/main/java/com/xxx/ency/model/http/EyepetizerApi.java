package com.xxx.ency.model.http;

import com.xxx.ency.model.bean.DailyVideoBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xiarh on 2018/2/7.
 */

public interface EyepetizerApi {

    String HOST = "http://baobab.kaiyanapp.com/";

    /**
     * 日常视频
     *
     * @param page
     * @param udid
     * @return
     */
    @GET("api/v5/index/tab/allRec")
    Flowable<DailyVideoBean> getDailyVideo(@Query("page") int page, @Query("udid") String udid);


}
