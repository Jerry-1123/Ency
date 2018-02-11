package com.xxx.ency.view.eyepetizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.view.Window;
import android.widget.TextView;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseActivity;
import com.xxx.ency.model.bean.VideoBean;
import com.xxx.ency.util.ColorUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 详细视频信息
 * Created by xiarh on 2018/2/9.
 */

public class EyepetizerDetailActivity extends BaseActivity {

    @BindView(R.id.txt_video_title)
    TextView txtVideoTitle;
    @BindView(R.id.txt_video_subtitle)
    TextView txtVideoSubtitle;
    @BindView(R.id.txt_video_content)
    TextView txtVideoContent;
    @BindView(R.id.txt_video_collection)
    TextView txtVideoCollection;
    @BindView(R.id.txt_video_share)
    TextView txtVideoShare;
    @BindView(R.id.txt_video_reply)
    TextView txtVideoReply;

    private VideoBean.ItemListBean.DataBeanX videoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eyepetizer_detail;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initialize() {
        initPalette();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        videoBean = (VideoBean.ItemListBean.DataBeanX) bundle.get("data");
        txtVideoTitle.setText(videoBean.getContent().getData().getTitle());
        txtVideoSubtitle.setText(videoBean.getHeader().getDescription());
        txtVideoContent.setText(videoBean.getContent().getData().getDescription());
        txtVideoCollection.setText(videoBean.getContent().getData().getConsumption().getCollectionCount() + "");
        txtVideoShare.setText(videoBean.getContent().getData().getConsumption().getShareCount() + "");
        txtVideoReply.setText(videoBean.getContent().getData().getConsumption().getReplyCount() + "");
    }

    /**
     * 使用Palette改变状态颜色
     */
    private void initPalette() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_video);
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate();
        // 提取颜色
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@NonNull Palette palette) {
                // 有活力的暗色
                Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    // 很明显，这两货是新API才有的。
                    window.setStatusBarColor(ColorUtil.colorBurn(darkVibrant.getRgb()));
                    window.setNavigationBarColor(ColorUtil.colorBurn(darkVibrant.getRgb()));
                }
            }
        });
    }

    @OnClick(R.id.layout_share)
    public void onLayoutShareClick() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, videoBean.getContent().getData().getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, videoBean.getContent().getData().getPlayUrl());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(Intent.createChooser(intent, videoBean.getContent().getData().getTitle()));
    }
}
