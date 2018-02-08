package com.xxx.ency.view.eyepetizer;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPFragment;
import com.xxx.ency.config.Constants;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.contract.EyepetizerContract;
import com.xxx.ency.di.component.DaggerEyepetizerFragmentComponent;
import com.xxx.ency.di.module.EyepetizerFragmentModule;
import com.xxx.ency.model.bean.DailyVideoBean;
import com.xxx.ency.presenter.EyepetizerPresenter;
import com.xxx.ency.view.eyepetizer.adapter.EyepetizerDailyAdapter;

import butterknife.BindView;

/**
 * Created by xiarh on 2018/2/7.
 */

public class EyepetizerFragment extends BaseMVPFragment<EyepetizerPresenter> implements EyepetizerContract.View {

    @BindView(R.id.recyclerview_eyepetizer)
    RecyclerView recyclerView;

    private EyepetizerDailyAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_eyepetizer;
    }

    @Override
    protected void initInject() {
        DaggerEyepetizerFragmentComponent
                .builder()
                .appComponent(EncyApplication.getAppComponent())
                .eyepetizerFragmentModule(new EyepetizerFragmentModule())
                .build()
                .inject(this);
    }

    @Override
    protected void initialize() {
        mPresenter.getDailyVideo(1, Constants.EYEPETIZER_UDID);
        adapter = new EyepetizerDailyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showDailyVideoData(DailyVideoBean dailyBean) {
        adapter.setNewData(dailyBean.getItemList());
    }

    @Override
    public void failGetData() {

    }
}
