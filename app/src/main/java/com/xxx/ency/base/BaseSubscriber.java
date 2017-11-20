package com.xxx.ency.base;

import android.content.Context;

import com.xxx.ency.R;
import com.xxx.ency.util.AppNetWorkUtil;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Subscriber基类
 * Created by xiarh on 2017/11/13.
 */

public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {

    private Context context;

    private BaseView view;

    public BaseSubscriber(Context context, BaseView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!AppNetWorkUtil.isNetworkConnected(context)) {
            view.showError(context.getString(R.string.no_network));
            onComplete();
            return;
        }
    }

    @Override
    public void onError(Throwable t) {
        view.showError(t.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
