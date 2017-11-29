package com.xxx.ency.model.http;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by xiarh on 2017/11/29.
 */

public interface ImageApi {

    String HOST = "http://img.gank.io/";

    /**
     * 图片下载
     *
     * @param url 图片地址
     * @return
     */
    @GET
    Flowable<ResponseBody> download(@Url String url);
}
