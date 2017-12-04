package com.xxx.ency.view.one;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ScrollView;
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

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.fab_top)
    FloatingActionButton fabTop;
    @BindView(R.id.scrollView_one)
    ScrollView scrollView;

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
        fabTop.setVisibility(View.GONE);
        mPresenter.getData(Constants.ONE_URL);
    }

    @Override
    public void showOneBean(OneBean bean) {
        fabTop.setVisibility(View.VISIBLE);
        oneTitle.setText(bean.getTitle());
        oneAuthor.setText(bean.getAuthor());
        oneContent.setText(bean.getContent());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void failGetData() {
        fabTop.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        fabTop.setVisibility(View.GONE);
        mPresenter.getData(Constants.ONE_URL);
    }

    @OnClick(R.id.fab_top)
    public void toTop() {
        scrollView.fullScroll(ScrollView.FOCUS_UP);
    }

//    private MyHandler mHandler;

//    Message msg = mHandler.obtainMessage();
//    msg.obj =oneBean;
//    mHandler.sendMessage(msg);

//    static class MyHandler extends Handler {
//
//        private WeakReference<OneFragment> mOuter;
//
//        public MyHandler(OneFragment fragment) {
//            mOuter = new WeakReference<>(fragment);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            OneFragment oneFragment = mOuter.get();
//            if (oneFragment != null) {
//                OneBean bean = (OneBean) msg.obj;
//                oneFragment.oneTitle.setText(bean.getTitle());
//                oneFragment.oneAuthor.setText(bean.getAuthor());
//                oneFragment.oneContent.setText(bean.getContent());
//                oneFragment.swipeRefreshLayout.setRefreshing(false);
//            }
//        }
//    }
//    mHandler.removeCallbacksAndMessages(null);
}