package com.xxx.ency.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.xxx.ency.config.EncyApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Fragment基类
 * Created by xiarh on 2017/9/21.
 */

public abstract class BaseFragment extends SupportFragment{

    protected View mView;

    protected Activity mActivity;

    protected Context mContext;

    private Unbinder mUnBinder;

    protected abstract int getLayoutId();

    protected abstract void initialize();

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        mUnBinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initialize();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // leakcanary
        RefWatcher refWatcher = EncyApplication.getRefWatcher(mActivity);
        refWatcher.watch(this);
    }
}