package com.xxx.ency.view.eyepetizer.adapter;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xxx.ency.R;
import com.xxx.ency.config.GlideApp;
import com.xxx.ency.model.bean.DailyVideoBean;
import com.xxx.ency.util.SystemUtil;

/**
 * Created by xiarh on 2018/2/7.
 */

public class EyepetizerDailyAdapter extends BaseQuickAdapter<DailyVideoBean.ItemListBean, BaseViewHolder> {

    public EyepetizerDailyAdapter() {
        super(R.layout.item_eyepetizer_daily);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyVideoBean.ItemListBean item) {
        helper.setText(R.id.txt_video_duration, SystemUtil.second2Minute(item.getData().getContent().getData().getDuration()));
        helper.setText(R.id.txt_video_title, item.getData().getContent().getData().getTitle());
        helper.setText(R.id.txt_video_content, item.getData().getContent().getData().getAuthor().getName()+"/ #"
                +item.getData().getContent().getData().getCategory());
        GlideApp.with(mContext)
                .load(item.getData().getContent().getData().getCover().getDetail())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.LOW)
                .into((RoundedImageView) helper.getView(R.id.img_video));
        GlideApp.with(mContext)
                .load(item.getData().getContent().getData().getAuthor().getIcon())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.LOW)
                .into((RoundedImageView) helper.getView(R.id.img_video_author));
    }
}