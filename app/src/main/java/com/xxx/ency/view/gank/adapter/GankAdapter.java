package com.xxx.ency.view.gank.adapter;

import android.support.v4.view.ViewPager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.ency.R;
import com.xxx.ency.model.bean.GankBean;

import java.util.List;

/**
 * Created by xiarh on 2017/11/27.
 */

public class GankAdapter extends BaseMultiItemQuickAdapter<GankBean.ResultsBean, BaseViewHolder> {

    private boolean isPTP = false;

    public void setPTP(boolean ptp) {
        this.isPTP = ptp;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GankAdapter(List<GankBean.ResultsBean> data) {
        super(data);
        addItemType(GankBean.ResultsBean.TEXT, R.layout.item_gank_txt);
        addItemType(GankBean.ResultsBean.IMG, R.layout.item_gank_image);
        addItemType(GankBean.ResultsBean.MEIZI,R.layout.item_gank_meizi);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankBean.ResultsBean item) {
        switch (helper.getItemViewType()) {
            case GankBean.ResultsBean.TEXT:
                helper.setText(R.id.txt_gank_title, item.getDesc());
                helper.setText(R.id.txt_gank_author, item.getSource());
                helper.setText(R.id.txt_gank_time, item.getPublishedAt().substring(0, 10));
                break;
            case GankBean.ResultsBean.IMG:
                helper.setText(R.id.txt_gank_title, item.getDesc());
                helper.setText(R.id.txt_gank_author, item.getSource());
                helper.setText(R.id.txt_gank_time, item.getPublishedAt().substring(0, 10));
                ViewPager viewPager = helper.getView(R.id.viewpager_gank_img);
                ImageAdapter adapter = new ImageAdapter(mContext, item.getImages(),isPTP);
                viewPager.setAdapter(adapter);
                break;
            case GankBean.ResultsBean.MEIZI:
//                ImageView imageView = helper.getView(R.id.img_meizi);
//                GlideApp.with(mContext)
//                        .load(item.getUrl())
//                        .priority(Priority.LOW)
//                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                        .override(Target.SIZE_ORIGINAL)
//                        .into(imageView);
                break;
        }
    }
}
