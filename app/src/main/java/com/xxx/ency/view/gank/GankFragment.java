package com.xxx.ency.view.gank;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPFragment;
import com.xxx.ency.config.Constants;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.contract.GankContract;
import com.xxx.ency.di.component.DaggerGankFragmentComponent;
import com.xxx.ency.di.module.GankFragmentModule;
import com.xxx.ency.model.bean.GankBean;
import com.xxx.ency.model.prefs.SharePrefManager;
import com.xxx.ency.presenter.GankPresenter;
import com.xxx.ency.view.gank.adapter.GankAdapter;
import com.xxx.ency.view.web.WebActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by xiarh on 2017/11/27.
 */

public class GankFragment extends BaseMVPFragment<GankPresenter> implements GankContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Inject
    SharePrefManager sharePrefManager;

    private GankAdapter gankAdapter;

    private List<GankBean.ResultsBean> resultsBeans = new ArrayList<>();

    private String type;

    private int page = 1;

    private static final int PAGE_SIZE = 20;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh;
    }

    @Override
    protected void initInject() {
        DaggerGankFragmentComponent
                .builder()
                .appComponent(EncyApplication.getAppComponent())
                .gankFragmentModule(new GankFragmentModule())
                .build()
                .inject(this);
    }

    public static GankFragment newInstance(String type) {
        GankFragment gankFragment = new GankFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        gankFragment.setArguments(bundle);
        return gankFragment;
    }

    @Override
    protected void initialize() {
        Bundle args = getArguments();
        if (args != null) {
            type = args.getString("type");
        }
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        mPresenter.getGankData(type, PAGE_SIZE, page);
        mPresenter.getPTP();
        gankAdapter = new GankAdapter(resultsBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(gankAdapter);
        gankAdapter.setPTP(sharePrefManager.getProvincialTrafficPattern());
        gankAdapter.setOnLoadMoreListener(this, recyclerView);
        gankAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GankBean.ResultsBean bean = (GankBean.ResultsBean) adapter.getData().get(position);
                WebActivity.open(new WebActivity.Builder()
                        .setGuid(bean.get_id())//微信Item没有id，使用url作为guid
                        .setImgUrl("")
                        .setType(Constants.TYPE_GANK)
                        .setUrl(bean.getUrl())
                        .setTitle(bean.getDesc())
                        .setShowLikeIcon(true)
                        .setContext(mContext)
                );
            }
        });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getGankData(type, PAGE_SIZE, page);
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        gankAdapter.setEnableLoadMore(false);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getGankData(type, PAGE_SIZE, page);
        // 防止上拉加载的时候可以下拉刷新
        swipeRefreshLayout.setEnabled(false);
    }

    /**
     * 加载成功
     *
     * @param gankBean
     */
    @Override
    public void showGankData(GankBean gankBean) {
        if (null != swipeRefreshLayout && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            // 下拉刷新后可以上拉加载
            gankAdapter.setEnableLoadMore(true);
        }
        if (null != gankAdapter && gankAdapter.isLoading()) {
            // 上拉加载后可以下拉刷新
            swipeRefreshLayout.setEnabled(true);
        }
        if (page == 1) {
            resultsBeans.clear();
            resultsBeans.addAll(gankBean.getResults());
        } else {
            resultsBeans.addAll(gankBean.getResults());
        }
        gankAdapter.notifyDataSetChanged();
        if (gankBean.getResults().size() == PAGE_SIZE) {
            gankAdapter.loadMoreComplete();
        } else if (gankBean.getResults().size() < PAGE_SIZE) {
            gankAdapter.loadMoreEnd();
        }
    }

    /**
     * 加载失败
     */
    @Override
    public void failGetData() {
        gankAdapter.loadMoreFail();
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshAdapter(boolean isRefresh) {
        if (isRefresh) {
            gankAdapter.setPTP(sharePrefManager.getProvincialTrafficPattern());
            gankAdapter.notifyDataSetChanged();
        }
    }
}
