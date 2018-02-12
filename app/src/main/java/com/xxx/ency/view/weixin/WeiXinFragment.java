package com.xxx.ency.view.weixin;

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
import com.xxx.ency.contract.WeiXinContract;
import com.xxx.ency.di.component.DaggerWeiXinFragmentComponent;
import com.xxx.ency.di.module.WeiXinFragmentModule;
import com.xxx.ency.model.bean.WeiXinBean;
import com.xxx.ency.model.prefs.SharePrefManager;
import com.xxx.ency.presenter.WeiXinPresenter;
import com.xxx.ency.view.web.WebActivity;
import com.xxx.ency.view.weixin.adapter.WeiXinAdapter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 微信精选
 * Created by xiarh on 2017/11/8.
 */

public class WeiXinFragment extends BaseMVPFragment<WeiXinPresenter> implements WeiXinContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Inject
    SharePrefManager sharePrefManager;

    private WeiXinAdapter weiXinAdapter;

    private int page = 1;

    private static final int PAGE_SIZE = 20;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh;
    }

    @Override
    protected void initInject() {
        DaggerWeiXinFragmentComponent
                .builder()
                .appComponent(EncyApplication.getAppComponent())
                .weiXinFragmentModule(new WeiXinFragmentModule())
                .build()
                .inject(this);
    }

    @Override
    protected void initialize() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        mPresenter.getWeiXinData(PAGE_SIZE, page);
        mPresenter.getPTP();
        weiXinAdapter = new WeiXinAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(weiXinAdapter);
        weiXinAdapter.setPTP(sharePrefManager.getProvincialTrafficPattern());
        weiXinAdapter.setOnLoadMoreListener(this, recyclerView);
        weiXinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WeiXinBean.NewslistBean bean = (WeiXinBean.NewslistBean) adapter.getData().get(position);
                WebActivity.open(new WebActivity.Builder()
                        .setGuid(bean.getUrl())//微信Item没有id，使用url作为guid
                        .setImgUrl(bean.getPicUrl())
                        .setType(Constants.TYPE_WEIXIN)
                        .setUrl(bean.getUrl())
                        .setTitle(bean.getTitle())
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
        mPresenter.getWeiXinData(PAGE_SIZE, page);
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        weiXinAdapter.setEnableLoadMore(false);
    }

    /**
     * 上拉加载
     */

    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getWeiXinData(PAGE_SIZE, page);
        // 防止上拉加载的时候可以下拉刷新
        swipeRefreshLayout.setEnabled(false);
    }

    /**
     * 加载成功
     *
     * @param weiXinBean
     */
    @Override
    public void showWeiXinData(WeiXinBean weiXinBean) {
        if (null != swipeRefreshLayout && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            // 下拉刷新后可以上拉加载
            weiXinAdapter.setEnableLoadMore(true);
        }
        if (null != weiXinAdapter && weiXinAdapter.isLoading()) {
            // 上拉加载后可以下拉刷新
            swipeRefreshLayout.setEnabled(true);
        }
        if (page == 1) {
            weiXinAdapter.setNewData(weiXinBean.getNewslist());
        } else {
            weiXinAdapter.addData(weiXinBean.getNewslist());
        }
        if (weiXinBean.getNewslist().size() == PAGE_SIZE) {
            weiXinAdapter.loadMoreComplete();
        } else if (weiXinBean.getNewslist().size() < PAGE_SIZE) {
            weiXinAdapter.loadMoreEnd();
        }
    }

    /**
     * 加载失败
     */
    @Override
    public void failGetData() {
        weiXinAdapter.loadMoreFail();
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 省流量模式，刷新Adapter
     */
    @Override
    public void refreshAdapter(boolean isRefreshed) {
        if (isRefreshed) {
            weiXinAdapter.setPTP(sharePrefManager.getProvincialTrafficPattern());
            weiXinAdapter.notifyDataSetChanged();
        }
    }
}
