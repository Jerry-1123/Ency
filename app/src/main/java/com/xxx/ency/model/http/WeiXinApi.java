package com.xxx.ency.model.http;

import com.xxx.ency.model.bean.WeiXinBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 微信精选
 * Created by xiarh on 2017/11/8.
 */

public interface WeiXinApi {

    String HOST = "http://api.huceo.com/";

    /**
     * 微信数据
     *
     * @param key  秘钥
     * @param num  请求个数
     * @param page 页数
     * @return
     */
    @GET("wxnew/")
    Flowable<WeiXinBean> getWeiXin(@Query("key") String key, @Query("num") int num, @Query("page") int page);
}
