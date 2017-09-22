package com.xxx.ency;

import com.r0adkll.slidr.Slidr;
import com.xxx.ency.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        int primary = getResources().getColor(R.color.colorPrimary);
        int secondary = getResources().getColor(R.color.colorPrimaryDark);
        Slidr.attach(this, primary, secondary);
    }
}
