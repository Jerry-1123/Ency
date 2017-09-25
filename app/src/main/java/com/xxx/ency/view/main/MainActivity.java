package com.xxx.ency.view.main;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseActivity;

import butterknife.BindView;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTxtTitle;
    @BindView(R.id.menu_slide)
    FrameLayout mSlide;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawerlayout)
    DrawerLayout mDrawerLayout;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        setSupportActionBar(mToolbar);
        mTxtTitle.setText("主页");
        mSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }
}
