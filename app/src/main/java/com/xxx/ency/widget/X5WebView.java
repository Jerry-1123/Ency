package com.xxx.ency.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebView;

/**
 * Created by xiarh on 2018/2/1.
 */

public class X5WebView extends WebView {

    public interface IScrollListener {
        void onScrollChanged(int scrollY);
    }

    private IScrollListener mScrollListener;

    public void setOnScrollListener(IScrollListener listener) {
        mScrollListener = listener;
    }

    public X5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //只要是通过webview的滚动距离 t
        if (mScrollListener != null) {
            mScrollListener.onScrollChanged(t);
        }
    }
}
