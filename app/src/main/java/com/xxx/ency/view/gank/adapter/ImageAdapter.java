package com.xxx.ency.view.gank.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xxx.ency.R;
import com.xxx.ency.config.GlideApp;
import com.xxx.ency.view.gank.ImageActivity;

import java.util.List;

/**
 * Created by xiarh on 2017/11/28.
 */

public class ImageAdapter extends PagerAdapter {

    private List<String> imgs;

    private Context context;

    public ImageAdapter(Context context, List<String> imgs) {
        this.imgs = imgs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        GlideApp.with(context)
                .asBitmap()
                .load(imgs.get(position))
                .fitCenter()
                .placeholder(R.drawable.ic_ali)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra("imgurl", imgs.get(position));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, imageView, context.getString(R.string.transition_img));
                ActivityCompat.startActivity(context, intent, options.toBundle());
            }
        });
        container.addView(imageView);
        return imageView;
    }
}