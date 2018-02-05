package com.xxx.ency.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xxx.ency.R;
import com.xxx.ency.config.GlideApp;

/**
 * Created by xiarh on 2018/2/2.
 */

public class ImageLoader {

    public static void loadAll(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context)
                .load(imgUrl)
                .fitCenter()
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.LOW)
                .into(imageView);
    }

    public static void loadAll(Context context, int imgRes, ImageView imageView) {
        GlideApp.with(context)
                .load(imgRes)
                .fitCenter()
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.LOW)
                .into(imageView);
    }

    public static void loadNone(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context)
                .load(R.drawable.icon_default)
                .fitCenter()
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.LOW)
                .into(imageView);
    }

    public static void loadNone(Context context, int imgRes, ImageView imageView) {
        GlideApp.with(context)
                .load(imgRes)
                .fitCenter()
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.LOW)
                .into(imageView);
    }
}
