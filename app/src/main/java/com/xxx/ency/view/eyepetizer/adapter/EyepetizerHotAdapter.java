package com.xxx.ency.view.eyepetizer.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xxx.ency.R;
import com.xxx.ency.config.GlideApp;
import com.xxx.ency.model.bean.DailyVideoBean;
import com.xxx.ency.model.bean.HotVideoBean;
import com.xxx.ency.util.SystemUtil;

/**
 * Created by xiarh on 2018/2/7.
 */

public class EyepetizerHotAdapter extends BaseQuickAdapter<HotVideoBean.ItemListBean, BaseViewHolder> {

    public EyepetizerHotAdapter() {
        super(R.layout.item_eyepetizer_hot);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotVideoBean.ItemListBean item) {
        helper.setText(R.id.txt_video_duration, SystemUtil.second2Minute(item.getData().getDuration()));
        helper.setText(R.id.txt_video_title, item.getData().getTitle());
        helper.setText(R.id.txt_video_content, item.getData().getAuthor().getName() + " / #"
                + item.getData().getCategory());
        GlideApp.with(mContext)
                .load(item.getData().getCover().getDetail())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.LOW)
                .into((RoundedImageView) helper.getView(R.id.img_video));
        GlideApp.with(mContext)
                .load(item.getData().getAuthor().getIcon())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.LOW)
                .into((RoundedImageView) helper.getView(R.id.img_video_author));
        ImageView imgDaily = helper.getView(R.id.img_dialy);
        if (item.getData().getAuthor().getName().contains("每日编辑精选")) {
            imgDaily.setVisibility(View.VISIBLE);
        } else {
            imgDaily.setVisibility(View.GONE);
        }
    }
}