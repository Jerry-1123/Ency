package com.xxx.ency.view.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseActivity;
import com.xxx.ency.base.RxBus;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.model.prefs.SharePrefManager;
import com.xxx.ency.view.main.MainActivity;

import butterknife.BindView;

/**
 * 设置
 * Created by xiarh on 2017/12/28.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private SharePrefManager sharePrefManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initialize() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("设置");
        sharePrefManager = EncyApplication.getAppComponent().getSharePrefManager();
        getFragmentManager().beginTransaction().replace(R.id.content_setting, new SettingFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goBack() {
        int a = sharePrefManager.getLocalMode();
        int b = AppCompatDelegate.getDefaultNightMode();
        boolean c = sharePrefManager.getLocalProvincialTrafficPatterns();
        boolean d = sharePrefManager.getProvincialTrafficPattern();
        if(c != d){
            RxBus.getInstance().post(1001);
            sharePrefManager.setLocalProvincialTrafficPatterns(d);
        }
        if (a != b) {
            sharePrefManager.setLocalMode(AppCompatDelegate.getDefaultNightMode());
            RxBus.getInstance().post(1000);
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.open_enter, R.anim.open_exit);
        }
        finish();
    }
}
