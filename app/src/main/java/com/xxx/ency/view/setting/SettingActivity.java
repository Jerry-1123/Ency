package com.xxx.ency.view.setting;

import android.support.v7.widget.Toolbar;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by xiarh on 2017/12/28.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initialize() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("设置");
        getFragmentManager().beginTransaction().replace(R.id.content_setting, new SettingFragment()).commit();
    }
}
