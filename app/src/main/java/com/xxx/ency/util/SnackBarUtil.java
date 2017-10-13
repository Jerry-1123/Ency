package com.xxx.ency.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by xiarh on 2017/10/13.
 */

public class SnackBarUtil {

    public static void show(View view, CharSequence msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showShort(View view, CharSequence msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }
}
