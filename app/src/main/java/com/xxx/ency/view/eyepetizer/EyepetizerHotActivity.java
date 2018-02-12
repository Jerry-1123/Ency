package com.xxx.ency.view.eyepetizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.ency.R;
import com.xxx.ency.base.BaseActivity;
import com.xxx.ency.model.bean.VideoBean;
import com.xxx.ency.view.eyepetizer.adapter.EyepetizerAdapter;

import butterknife.BindView;

/**
 * Created by xiarh on 2018/2/8.
 */

public class EyepetizerHotActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview_eyepetizer)
    RecyclerView recyclerView;

    private EyepetizerAdapter hotAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eyepetizer;
    }

    @Override
    protected void initialize() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("热门排行");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        VideoBean hotVideoBean = (VideoBean) bundle.getSerializable("data");

        hotAdapter = new EyepetizerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(hotAdapter);
        hotAdapter.setNewData(hotVideoBean.getItemList());
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoBean.ItemListBean bean = (VideoBean.ItemListBean) adapter.getData().get(position);
                VideoBean.ItemListBean.DataBeanX beanX = bean.getData();
                Intent intent = new Intent(mContext, EyepetizerDetailActivity.class);
                intent.putExtra("data", beanX);
                mContext.startActivity(intent);
            }
        });
    }
}

