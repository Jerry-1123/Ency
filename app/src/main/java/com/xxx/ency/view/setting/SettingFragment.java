package com.xxx.ency.view.setting;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;

import com.xxx.ency.R;
import com.xxx.ency.config.Constants;
import com.xxx.ency.model.prefs.SharePrefManager;
import com.xxx.ency.util.AppApplicationUtil;
import com.xxx.ency.util.SnackBarUtils;
import com.xxx.ency.util.SystemUtil;
import com.xxx.ency.view.web.WebActivity;

/**
 * 设置
 * Created by xiarh on 2018/1/3.
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private SwitchPreference provincialFlowPreference;

    private SwitchPreference nightModePreference;

    private Preference cleanCachePreference;

    private Preference versionPreference;

    private Preference homepagePreference;

    private SharePrefManager sharePrefManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        sharePrefManager = new SharePrefManager(getActivity());

        provincialFlowPreference = (SwitchPreference) findPreference(getResources().getString(R.string.key_provincial_traffic_patterns));
        nightModePreference = (SwitchPreference) findPreference(getResources().getString(R.string.key_night_mode));
        cleanCachePreference = findPreference(getResources().getString(R.string.key_clear_cache));
        versionPreference = findPreference(getResources().getString(R.string.key_version));
        homepagePreference = findPreference(getResources().getString(R.string.key_homepage));

        provincialFlowPreference.setOnPreferenceChangeListener(this);
        nightModePreference.setOnPreferenceChangeListener(this);
        cleanCachePreference.setOnPreferenceClickListener(this);
        versionPreference.setOnPreferenceClickListener(this);
        homepagePreference.setOnPreferenceClickListener(this);

        // 设置当前版本号
        versionPreference.setSummary("V " + AppApplicationUtil.getVersionName(getActivity()));

        // 设置缓存大小
        cleanCachePreference.setSummary("缓存大小：" + SystemUtil.getTotalCacheSize(getActivity()));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == provincialFlowPreference) {
            sharePrefManager.setProvincialTrafficPatterns((Boolean) newValue);
        } else if (preference == nightModePreference) {
            sharePrefManager.setNightMode((Boolean) newValue);
            int currentMode = (Boolean) newValue ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
            AppCompatDelegate.setDefaultNightMode(currentMode);
            // recreate()会产生闪屏
//            if (getActivity() instanceof SettingActivity) {
//                getActivity().recreate();
//            }
            // 调用startActivity启动，并为其添加了一个透明渐变的启动动画，最后调用finish结束掉旧的页面。
            getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
            getActivity().overridePendingTransition(R.anim.alpha_start, R.anim.alpha_out);
            getActivity().finish();
        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == cleanCachePreference) {
            SystemUtil.clearAllCache(getActivity());
            SnackBarUtils.show(getView(), "缓存已清除 (*^__^*)");
            cleanCachePreference.setSummary("缓存大小：" + SystemUtil.getTotalCacheSize(getActivity()));
        } else if (preference == versionPreference) {

        } else if (preference == homepagePreference) {
            WebActivity.open(new WebActivity.Builder()
                    .setGuid("")
                    .setImgUrl("")
                    .setType(Constants.TYPE_DEFAULT)
                    .setUrl("https://github.com/xiarunhao123")
                    .setTitle("个人主页")
                    .setShowLikeIcon(false)
                    .setContext(getActivity()));
        }
        return false;
    }
}