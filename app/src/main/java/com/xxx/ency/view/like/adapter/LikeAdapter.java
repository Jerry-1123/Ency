package com.xxx.ency.view.like.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.ency.R;
import com.xxx.ency.config.Constants;
import com.xxx.ency.config.GlideApp;
import com.xxx.ency.model.bean.LikeBean;
import com.xxx.ency.util.DateUtil;

/**
 * Created by xiarh on 2017/11/23.
 */

public class LikeAdapter extends BaseQuickAdapter<LikeBean, BaseViewHolder> {

    public LikeAdapter() {
        super(R.layout.item_like);
    }

    @Override
    protected void convert(BaseViewHolder helper, LikeBean item) {
        helper.setText(R.id.txt_like_title, item.getTitle());
        if (item.getType() == Constants.TYPE_WEIXIN) {
            helper.setText(R.id.txt_like_type, R.string.weixin);
        }
        helper.setText(R.id.txt_like_date, DateUtil.LongToString(item.getTime()));
        GlideApp.with(mContext)
                .load(item.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.icon_default)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView) helper.getView(R.id.img_like));
    }
}
