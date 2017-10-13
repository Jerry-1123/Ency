package com.xxx.ency.config;

import java.io.File;

/**
 * 常量
 * Created by xiarh on 2017/9/21.
 */

public class Constants {

    public static final String TEST_URL = "http://gdown.baidu.com/data/wisegame/405c40b335033fe1/shoujitaobao_166.apk";

    // bugly APP ID
    public static final String BUGLY_APP_ID = "e0359610ba";

    // 和风天气Key
    public static final String WEATHER_KEY = "33ebf4d7998a4548942adcad5582e503";

    // fir.im API Token
    public static final String FIR_IM_API_TOKEN = "ad06cab6f35eb810c666a7b3936e9119";

    // fir.im ID
    public static final String FIR_IM_ID = "59e01d00959d691da60001b0";

    public static final String PATH_DATA = EnycApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
}
