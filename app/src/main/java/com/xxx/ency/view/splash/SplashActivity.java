package com.xxx.ency.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.xxx.ency.R;
import com.xxx.ency.view.main.MainActivity;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * 启动页
 * Created by xiarh on 2017/12/27.
 */

public class SplashActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 1000);
    }
}
