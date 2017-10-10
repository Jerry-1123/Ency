package com.xxx.ency.model.http.api;

import com.xxx.ency.model.bean.WeatherBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xiarh on 2017/9/25.
 */

public interface WeatherApi {

    String HOST = "https://free-api.heweather.com/";

    /**
     * 天气预报
     *
     * @param city
     * @return
     */
    @GET("v5/now")  //没有数据就填 . 或者 /
    Observable<WeatherBean> getWeather(@Query("city") String city, @Query("key") String key);
}
