package com.xxx.ency.view.eyepetizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseActivity;
import com.xxx.ency.model.bean.VideoBean;

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
