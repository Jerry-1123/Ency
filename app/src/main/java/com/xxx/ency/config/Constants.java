package com.xxx.ency.config;

import java.io.File;

/**
 * 常量
 * Created by xiarh on 2017/9/21.
 */

public class Constants {

    // bugly APP ID
    public static final String BUGLY_APP_ID = "e0359610ba";

    public static final String PATH_DATA = EnycApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
}
