package com.xxx.ency.model.http;

import com.xxx.ency.config.Constants;
import com.xxx.ency.model.bean.UpdateBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 版本更新
 * Created by xiarh on 2017/10/13.
 */

public interface UpdateApi {

    String HOST = "http://api.fir.im/apps/latest/";

    /**
     * 获取最新版本信息
     *
     * @param api_token
     * @return
     */
    @GET(Constants.FIR_IM_ID)
    Flowable<UpdateBean> getVersionInfo(@Query("api_token") String api_token);
}
