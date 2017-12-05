package com.xxx.ency.view.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.ClipboardManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseActivity;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.model.bean.LikeBean;
import com.xxx.ency.model.db.GreenDaoManager;
import com.xxx.ency.util.SnackBarUtil;

import butterknife.BindView;

/**
 * Created by xiarh on 2017/11/7.
 */

public class WebActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.swipelayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private String guid;

    private String imageUrl;

    private int type;

    private String url;

    private String title;

    private boolean isShowLikeIcon;

    private MenuItem menuItem;

    private GreenDaoManager daoManager;

    private boolean isLiked;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initialize() {
        daoManager = EncyApplication.getAppComponent().getGreenDaoManager();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            guid = bundle.getString("guid");
            imageUrl = bundle.getString("imageUrl");
            type = bundle.getInt("type");
            url = bundle.getString("url");
            title = bundle.getString("title");
            isShowLikeIcon = bundle.getBoolean("isshow");
        }
        setTitle(title);
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setGeolocationDatabasePath(dir);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                swipeRefreshLayout.setRefreshing(true);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
        //点击返回
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public void onRefresh() {
        webView.reload();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isShowLikeIcon) {
            getMenuInflater().inflate(R.menu.menu_web1, menu);
            menuItem = menu.findItem(R.id.item_like);
            setLikeState(daoManager.queryByGuid(guid));
        } else {
            getMenuInflater().inflate(R.menu.menu_web2, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_like:
                if (isLiked) {
                    item.setIcon(R.drawable.ic_notlike);
                    daoManager.deleteByGuid(guid);
                    isLiked = false;
                    SnackBarUtil.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), "成功从收藏中移除");
                } else {
                    item.setIcon(R.drawable.ic_like);
                    LikeBean bean = new LikeBean();
                    bean.setId(null);
                    bean.setGuid(guid);
                    bean.setImageUrl(imageUrl);
                    bean.setTitle(title);
                    bean.setUrl(url);
                    bean.setType(type);
                    bean.setTime(System.currentTimeMillis());
                    daoManager.insert(bean);
                    isLiked = true;
                    SnackBarUtil.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), "成功添加到收藏");
                }
                break;
            case R.id.item_copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(url);
                SnackBarUtil.show(webView, R.string.copy_msg, this);
                break;
            case R.id.item_browser:
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setLikeState(boolean state) {
        if (state) {
            menuItem.setIcon(R.drawable.ic_like);
            isLiked = true;
        } else {
            menuItem.setIcon(R.drawable.ic_notlike);
            isLiked = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearCache(true);
        webView.clearHistory();
        webView.removeAllViews();
    }

    public static class Builder {

        private String guid;
        private String imageUrl;
        private int type;
        private String url;
        private String title;
        private boolean isShowLikeIcon;
        private Context context;

        public Builder() {

        }

        public Builder setGuid(String guid) {
            this.guid = guid;
            return this;
        }

        public Builder setImgUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setShowLikeIcon(boolean isShowLikeIcon) {
            this.isShowLikeIcon = isShowLikeIcon;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }
    }

    public static void open(Builder builder) {
        Intent intent = new Intent(builder.context, WebActivity.class);
        intent.putExtra("guid", builder.guid);
        intent.putExtra("imageUrl", builder.imageUrl);
        intent.putExtra("type", builder.type);
        intent.putExtra("url", builder.url);
        intent.putExtra("title", builder.title);
        intent.putExtra("isshow", builder.isShowLikeIcon);
        builder.context.startActivity(intent);
    }
}