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

    public static void loadDefault(Context context, ImageView imageView) {
        GlideApp.with(context)
                .load(R.drawable.icon_default)
                .centerCrop()
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }

    public static void loadAll(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context)
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }

    public static void loadAll(Context context, int imgRes, ImageView imageView) {
        GlideApp.with(context)
                .load(imgRes)
                .centerCrop()
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadAllNoPlaceHolder(Context context, String imgUrl, int imgRes, ImageView imageView) {
        GlideApp.with(context)
                .load(imgUrl)
                .centerCrop()
                .error(imgRes)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    public static void loadAllNoPlaceHolder(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context)
                .load(imgUrl)
                .centerCrop()
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    public static void loadAllAsBitmap(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(imgUrl)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }

    public static void loadAllAsBitmap(Context context, String imgUrl, int imgRes, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(imgUrl)
                .centerCrop()
                .placeholder(imgRes)
                .error(imgRes)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.LOW)
                .into(imageView);
    }
}
