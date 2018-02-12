package com.xxx.ency.view.weixin.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.ency.R;
import com.xxx.ency.model.bean.WeiXinBean;
import com.xxx.ency.util.AppNetWorkUtil;
import com.xxx.ency.util.ImageLoader;

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
            ImageLoader.loadDefault(mContext,(ImageView) helper.getView(R.id.img_weixin));
        } else {
            ImageLoader.loadAll(mContext,item.getPicUrl(),(ImageView) helper.getView(R.id.img_weixin));
        }
    }
}
