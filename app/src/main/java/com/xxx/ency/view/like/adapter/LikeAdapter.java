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

    private boolean isPTP = false;

    public void setPTP(boolean ptp) {
        this.isPTP = ptp;
    }

    public LikeAdapter() {
        super(R.layout.item_like);
    }

    @Override
    protected void convert(BaseViewHolder helper, LikeBean item) {
        helper.setText(R.id.txt_like_title, item.getTitle());
        if (item.getType() == Constants.TYPE_WEIXIN) {
            helper.setText(R.id.txt_like_type, R.string.weixin);
        } else if (item.getType() == Constants.TYPE_GANK) {
            helper.setText(R.id.txt_like_type, R.string.gank);
        }
        helper.setText(R.id.txt_like_date, DateUtil.LongToString(item.getTime()));
        if (isPTP) {
            GlideApp.with(mContext)
                    .load(R.drawable.ic_ali)
                    .fitCenter()
                    .placeholder(R.drawable.ic_ali)
                    .error(R.drawable.ic_ali)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.LOW)
                    .into((ImageView) helper.getView(R.id.img_like));
        } else {
            GlideApp.with(mContext)
                    .load(item.getImageUrl())
                    .fitCenter()
                    .placeholder(R.drawable.ic_ali)
                    .error(R.drawable.ic_ali)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.LOW)
                    .into((ImageView) helper.getView(R.id.img_like));
        }
    }
}
