package com.xxx.ency.view.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.ClipboardManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.xxx.ency.R;
import com.xxx.ency.base.BaseActivity;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.model.bean.LikeBean;
import com.xxx.ency.model.db.GreenDaoManager;
import com.xxx.ency.model.prefs.SharePrefManager;
import com.xxx.ency.util.SnackBarUtils;
import com.xxx.ency.widget.X5WebView;

import butterknife.BindView;

/**
 * Created by xiarh on 2017/11/7.
 */

public class WebActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    X5WebView webView;
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

    private SharePrefManager sharePrefManager;

    private boolean isLiked;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initialize() {
        daoManager = EncyApplication.getAppComponent().getGreenDaoManager();
        sharePrefManager = EncyApplication.getAppComponent().getSharePrefManager();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(SwipeRefreshLayout parent, @Nullable View child) {
                if (child.getScrollY() > 0)
                    return true;
                return false;
            }
        });
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
        settings.setGeolocationEnabled(true);
        settings.setGeolocationDatabasePath(dir);
        if (sharePrefManager.getNightMode()) {
            webView.setDayOrNight(false);
        } else {
            webView.setDayOrNight(true);
        }
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return super.shouldOverrideUrlLoading(webView, s);
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
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
                sslErrorHandler.proceed();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            //扩展支持alert事件
            @Override
            public boolean onJsAlert(WebView webView, String url, String message, final JsResult jsResult) {
                new MaterialDialog.Builder(mContext)
                        .title("提示")
                        .content(message)
                        .positiveText(R.string.yes)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                jsResult.confirm();
                            }
                        })
                        .cancelable(false)
                        .build()
                        .show();
                return true;
            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissionsCallback callback) {
                final boolean remember = false;
                new MaterialDialog.Builder(mContext)
                        .title("地理位置授权")
                        .content("允许" + origin + "获取您当前的地理位置信息吗")
                        .positiveText("允许")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                callback.invoke(origin, true, remember);
                            }
                        })
                        .negativeText("拒绝")
                        .negativeColorRes(R.color.black)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                callback.invoke(origin, false, remember);
                            }
                        })
                        .cancelable(false)
                        .build()
                        .show();
            }
        });
        webView.setOnScrollListener(new X5WebView.IScrollListener() {
            @Override
            public void onScrollChanged(int scrollY) {
                //swiperefreshLayout刷新
                //webView在顶部
                if (scrollY == 0) {
                    swipeRefreshLayout.setEnabled(true);
                }
                //webView不是顶部
                else {
                    swipeRefreshLayout.setEnabled(false);
                }
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
                    SnackBarUtils.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), "成功从收藏中移除");
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
                    SnackBarUtils.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), "成功添加到收藏");
                }
                break;
            case R.id.item_copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(url);
                SnackBarUtils.show(webView, R.string.copy_msg, this);
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