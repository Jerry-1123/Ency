package com.xxx.ency.model.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by xiarh on 2017/9/21.
 */

public class SharePrefManager {

    private static final String SHAREDPREFERENCES_NAME = "my_sp";

    private SharedPreferences SPfres;

    @Inject
    public SharePrefManager(Context context) {
        SPfres = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
