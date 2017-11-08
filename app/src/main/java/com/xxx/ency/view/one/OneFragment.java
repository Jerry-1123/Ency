package com.xxx.ency.view.one;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPFragment;
import com.xxx.ency.config.Constants;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.contract.OneContract;
import com.xxx.ency.di.component.DaggerOneFragmentComponent;
import com.xxx.ency.di.module.OneFragmentModule;
import com.xxx.ency.model.bean.OneBean;
import com.xxx.ency.presenter.OnePresenter;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * 每日一文
 * Created by xiarh on 2017/11/1.
 */

public class OneFragment extends BaseMVPFragment<OnePresenter> implements OneContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.one_title)
    TextView oneTitle;
    @BindView(R.id.one_author)
    TextView oneAuthor;
    @BindView(R.id.one_content)
    TextView oneContent;

    private MyHandler mHandler;

    @Override
    protected void initInject() {
        DaggerOneFragmentComponent.builder()
                .appComponent(EncyApplication.getAppComponent())
                .oneFragmentModule(new OneFragmentModule())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initialize() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        mHandler = new MyHandler(this);
        mPresenter.getData(Constants.ONE_URL);
    }

    @Override
    public void showOneBean(OneBean oneBean) {
        Message msg = mHandler.obtainMessage();
        msg.obj = oneBean;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onRefresh() {
        mPresenter.getData(Constants.ONE_URL);
    }

    static class MyHandler extends Handler {

        private WeakReference<OneFragment> mOuter;

        public MyHandler(OneFragment fragment) {
            mOuter = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            OneFragment oneFragment = mOuter.get();
            if (oneFragment != null) {
                OneBean bean = (OneBean) msg.obj;
                oneFragment.oneTitle.setText(bean.getTitle());
                oneFragment.oneAuthor.setText(bean.getAuthor());
                oneFragment.oneContent.setText(bean.getContent());
                oneFragment.swipeRefreshLayout.setRefreshing(false);
            }
        }
    }
}