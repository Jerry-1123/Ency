package com.xxx.ency.model.http;

import com.xxx.ency.model.bean.GankBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 干货热门
 * Created by xiarh on 2017/11/27.
 */

public interface GankApi {

    String HOST = "http://www.gank.io/";

    /**
     * 干货数据
     *
     * @param type 数据类型：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param size 请求个数
     * @param page 页数
     */
    @GET("api/data/{type}/{size}/{page}")
    Flowable<GankBean> getGanHuoDatas(@Path("type") String type, @Path("size") int size, @Path("page") int page);
}
