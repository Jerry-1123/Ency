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

    public static void loadDefault(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context)
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .priority(Priority.LOW)
                .into(imageView);
    }

    public static void loadAll(Context context, String imgUrl, ImageView imageView){
        GlideApp.with(context)
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.icon_default)
                .error(R.drawable.icon_default)
                .priority(Priority.LOW)
                .into(imageView);
    }
}
