package com.xxx.ency.model.http.api;

import com.xxx.ency.model.bean.WeatherBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xiarh on 2017/9/25.
 */

public interface WeatherApi {

    String Host = "https://api.heweather.com/v5/now";

    /**
     * 天气预报
     *
     * @param city
     * @return
     */
    @GET()
    Observable<WeatherBean> getWeather(@Query("city") String city, @Query("key") String key);
}
