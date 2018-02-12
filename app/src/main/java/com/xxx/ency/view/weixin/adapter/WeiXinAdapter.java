package com.xxx.ency.view.weixin.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.ency.R;
import com.xxx.ency.config.GlideApp;
import com.xxx.ency.model.bean.WeiXinBean;
import com.xxx.ency.util.AppNetWorkUtil;

/**
 * Created by xiarh on 2017/11/8.
 */

public class WeiXinAdapter extends BaseQuickAdapter<WeiXinBean.NewslistBean, BaseViewHolder> {

    private boolean isPTP = false;

    public void setPTP(boolean ptp) {
        this.isPTP = ptp;
    }

    public WeiXinAdapter() {
        super(R.layout.item_weixin);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeiXinBean.NewslistBean item) {
        helper.setText(R.id.txt_weixin_title, item.getTitle());
        helper.setText(R.id.txt_weixin_author, item.getDescription());
        helper.setText(R.id.txt_weixin_date, item.getCtime());
        if (isPTP && AppNetWorkUtil.getNetworkType(mContext) == AppNetWorkUtil.TYPE_MOBILE) {
            GlideApp.with(mContext)
                    .load(R.drawable.icon_default)
                    .fitCenter()
                    .placeholder(R.drawable.icon_default)
                    .error(R.drawable.icon_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.LOW)
                    .into((ImageView) helper.getView(R.id.img_weixin));
        } else {
            GlideApp.with(mContext)
                    .load(item.getPicUrl())
                    .centerCrop()
                    .placeholder(R.drawable.icon_default)
                    .error(R.drawable.icon_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.LOW)
                    .into((ImageView) helper.getView(R.id.img_weixin));
        }
    }
}
