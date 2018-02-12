package com.xxx.ency.view.eyepetizer.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xxx.ency.R;
import com.xxx.ency.model.bean.VideoBean;
import com.xxx.ency.util.ImageLoader;

/**
 * Created by xiarh on 2018/2/11.
 */

public class EyepetizerTagAdapter extends BaseQuickAdapter<VideoBean.ItemListBean.DataBeanX.ContentBean.DataBean.TagBean, BaseViewHolder> {

    public EyepetizerTagAdapter() {
        super(R.layout.item_eyepetizer_tag);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean.ItemListBean.DataBeanX.ContentBean.DataBean.TagBean item) {
        helper.setText(R.id.txt_video_tag_name, "#" + item.getName() + "#");
        RoundedImageView imageView = helper.getView(R.id.img_video_tag);
        ImageLoader.loadAllNoPlaceHolder(mContext,item.getBgPicture(),imageView);
        imageView.setAlpha(0.9f);
    }
}
