package com.xxx.ency.view.eyepetizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xxx.ency.R;
import com.xxx.ency.base.BaseActivity;
import com.xxx.ency.config.Constants;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.config.GlideApp;
import com.xxx.ency.model.bean.LikeBean;
import com.xxx.ency.model.bean.VideoBean;
import com.xxx.ency.model.db.GreenDaoManager;
import com.xxx.ency.util.ColorUtil;
import com.xxx.ency.view.eyepetizer.adapter.EyepetizerTagAdapter;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

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
    @BindView(R.id.img_video_collection)
    ImageView imgVideoCollection;
    @BindView(R.id.txt_video_collection)
    TextView txtVideoCollection;
    @BindView(R.id.txt_video_share)
    TextView txtVideoShare;
    @BindView(R.id.txt_video_reply)
    TextView txtVideoReply;
    @BindView(R.id.videoplayer)
    JZVideoPlayerStandard videoPlayerStandard;
    @BindView(R.id.recyclerview_tag)
    RecyclerView recyclerviewTag;
    @BindView(R.id.img_video_author)
    RoundedImageView imgVideoAuthor;
    @BindView(R.id.txt_video_author_name)
    TextView txtVideoAuthorName;
    @BindView(R.id.txt_video_author_description)
    TextView txtVideoAuthorDescription;

    private VideoBean.ItemListBean.DataBeanX videoBean;

    private EyepetizerTagAdapter tagAdapter;

    private GreenDaoManager daoManager;

    private Boolean isLiked;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eyepetizer_detail;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initialize() {
        initPalette();
        initData();
        initVideoPlayer();
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
                // 柔和的颜色
                Palette.Swatch muted = palette.getMutedSwatch();
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    // 很明显，这两货是新API才有的。
                    window.setStatusBarColor(ColorUtil.colorBurn(muted.getRgb()));
                    window.setNavigationBarColor(ColorUtil.colorBurn(muted.getRgb()));
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        videoBean = (VideoBean.ItemListBean.DataBeanX) bundle.get("data");
        txtVideoTitle.setText(videoBean.getContent().getData().getTitle());
        txtVideoSubtitle.setText(videoBean.getHeader().getDescription());
        txtVideoContent.setText(videoBean.getContent().getData().getDescription());
        txtVideoShare.setText(videoBean.getContent().getData().getConsumption().getShareCount() + "");
        txtVideoReply.setText(videoBean.getContent().getData().getConsumption().getReplyCount() + "");
        GlideApp.with(mContext)
                .load(videoBean.getContent().getData().getAuthor().getIcon())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.LOW)
                .into(imgVideoAuthor);
        txtVideoAuthorName.setText(videoBean.getContent().getData().getAuthor().getName());
        txtVideoAuthorDescription.setText(videoBean.getContent().getData().getAuthor().getDescription());
        tagAdapter = new EyepetizerTagAdapter();
        tagAdapter.setNewData(videoBean.getContent().getData().getTags().subList(0, 3));
        recyclerviewTag.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerviewTag.setAdapter(tagAdapter);
        daoManager = EncyApplication.getAppComponent().getGreenDaoManager();
        setLikeState(daoManager.queryByGuid(videoBean.getHeader().getId() + ""));
    }

    private void initVideoPlayer() {
        videoPlayerStandard.backButton.setVisibility(View.VISIBLE);
        videoPlayerStandard.titleTextView.setTextSize(16);
        videoPlayerStandard.setUp(videoBean.getContent().getData().getPlayUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, videoBean.getContent().getData().getTitle());
        GlideApp.with(mContext)
                .load(videoBean.getContent().getData().getCover().getFeed())
                .centerCrop()
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(videoPlayerStandard.thumbImageView);
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
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

    @OnClick(R.id.img_video_collection)
    public void onImgVideoCollectionClick() {
        if (isLiked) {
            imgVideoCollection.setImageResource(R.drawable.icon_uncollect);
            txtVideoCollection.setText("收藏");
            daoManager.deleteByGuid(videoBean.getHeader().getId() + "");
            isLiked = false;
        } else {
            imgVideoCollection.setImageResource(R.drawable.icon_collect);
            txtVideoCollection.setText("已收藏");
            LikeBean bean = new LikeBean();
            bean.setId(null);
            bean.setGuid(videoBean.getHeader().getId() + "");
            bean.setImageUrl(videoBean.getContent().getData().getCover().getDetail());
            bean.setTitle(videoBean.getContent().getData().getTitle());
            bean.setUrl(videoBean.getContent().getData().getPlayUrl());
            bean.setType(Constants.TYPE_VIDEO);
            bean.setTime(System.currentTimeMillis());
            daoManager.insert(bean);
            isLiked = true;
        }
    }

    private void setLikeState(boolean state) {
        if (state) {
            imgVideoCollection.setImageResource(R.drawable.icon_collect);
            txtVideoCollection.setText("已收藏");
            isLiked = true;
        } else {
            imgVideoCollection.setImageResource(R.drawable.icon_uncollect);
            txtVideoCollection.setText("收藏");
            isLiked = false;
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressedSupport();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
        //Change these two variables back
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }
}