package com.xxx.ency.util;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.xxx.ency.R;

/**
 * Created by xiarh on 2017/10/13.
 */

public class SnackBarUtil {

    public static void show(View view, CharSequence msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        ((TextView)(snackbar.getView().findViewById(R.id.snackbar_text))).setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void showShort(View view, CharSequence msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        ((TextView)(snackbar.getView().findViewById(R.id.snackbar_text))).setTextColor(Color.WHITE);
        snackbar.show();
    }
}
