package com.xxx.ency.view.gank;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPFragment;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.contract.GankContract;
import com.xxx.ency.di.component.DaggerGankFragmentComponent;
import com.xxx.ency.di.module.GankFragmentModule;
import com.xxx.ency.model.bean.GankBean;
import com.xxx.ency.presenter.GankPresenter;

import butterknife.BindView;

/**
 * Created by xiarh on 2017/11/27.
 */

public class GankFragment extends BaseMVPFragment<GankPresenter> implements GankContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private String type;

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

    public static GankFragment newInstance(String type){
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
    }

    @Override
    public void showGankData(GankBean gankBean) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void failGetData() {

    }
}
