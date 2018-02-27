package com.xxx.ency.view.like;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.ency.R;
import com.xxx.ency.base.BaseFragment;
import com.xxx.ency.config.Constants;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.model.bean.LikeBean;
import com.xxx.ency.model.db.GreenDaoManager;
import com.xxx.ency.model.prefs.SharePrefManager;
import com.xxx.ency.view.like.adapter.LikeAdapter;
import com.xxx.ency.view.web.WebActivity;

import javax.inject.Inject;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 我的收藏
 * Created by xiarh on 2017/11/23.
 */

public class LikeFragment extends BaseFragment {

    @BindView(R.id.recyclerview_like)
    RecyclerView recyclerView;

    @Inject
    SharePrefManager sharePrefManager;

    private LikeAdapter likeAdapter;

    private GreenDaoManager daoManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_like;
    }

    @Override
    protected void initialize() {
        daoManager = EncyApplication.getAppComponent().getGreenDaoManager();
        likeAdapter = new LikeAdapter();
        likeAdapter.setNewData(daoManager.queryAll());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(likeAdapter);
        likeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LikeBean bean = (LikeBean) adapter.getData().get(position);
                if (bean.getType() == Constants.TYPE_VIDEO) {
                    JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    JZVideoPlayerStandard.startFullscreen(mContext, JZVideoPlayerStandard.class, bean.getUrl(), bean.getTitle());
                } else {
                    WebActivity.open(new WebActivity.Builder()
                            .setGuid(bean.getUrl())
                            .setImgUrl(bean.getImageUrl())
                            .setType(bean.getType())
                            .setUrl(bean.getUrl())
                            .setTitle(bean.getTitle())
                            .setShowLikeIcon(true)
                            .setContext(mContext)
                    );
                }
            }
        });
        likeAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                final LikeBean bean = (LikeBean) adapter.getData().get(position);
                new MaterialDialog.Builder(mContext)
                        .content("确认要删除该收藏吗？")
                        .negativeText("取消")
                        .negativeColorRes(R.color.colorNegative)
                        .positiveText("确定")
                        .positiveColorRes(R.color.colorPositive)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                daoManager.delete(bean);
                                likeAdapter.remove(position);
                            }
                        })
                        .show();
                return true;
            }
        });
        likeAdapter.setEmptyView(R.layout.view_empty, recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isInited) {
            likeAdapter.setNewData(daoManager.queryAll());
        }
        if (null != sharePrefManager && null != likeAdapter) {
            likeAdapter.setPTP(sharePrefManager.getProvincialTrafficPattern());
            likeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (JZVideoPlayer.backPress()) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}