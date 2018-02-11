package com.xxx.ency.config;

import java.io.File;

/**
 * 常量
 * Created by xiarh on 2017/9/21.
 */

public class Constants {

    public static final String ONE_URL = "https://meiriyiwen.com/";

    // 微信精选Key
    public static final String WEIXIN_KEY = "1ae28fc9dd5afadc696ad94cd59426d8";

    // bugly APP ID
    public static final String BUGLY_APP_ID = "e0359610ba";

    // 和风天气Key
    public static final String WEATHER_KEY = "33ebf4d7998a4548942adcad5582e503";

    // fir.im API Token
    public static final String FIR_IM_API_TOKEN = "ad06cab6f35eb810c666a7b3936e9119";

    // fir.im ID
    public static final String FIR_IM_ID = "59e01d00959d691da60001b0";

    // 开眼视频pdid
    public static final String EYEPETIZER_UDID = "79a95dc6b649489383e976b5b97d129f6d592fad";

    public static final String PATH_DATA = EncyApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final int TYPE_DEFAULT = 0;

    public static final int TYPE_WEIXIN = 1;

    public static final int TYPE_GANK = 2;

    public static final int TYPE_VIDEO = 3;
}