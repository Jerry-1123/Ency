package com.xxx.ency.view.setting;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;

import com.xxx.ency.R;
import com.xxx.ency.config.Constants;
import com.xxx.ency.model.prefs.SharePrefManager;
import com.xxx.ency.util.SnackBarUtil;
import com.xxx.ency.util.SystemUtil;
import com.xxx.ency.view.web.WebActivity;

/**
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

        // 设置缓存大小
        cleanCachePreference.setSummary("缓存大小：" + SystemUtil.getTotalCacheSize(getActivity()));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == provincialFlowPreference) {
            sharePrefManager.setProvincialTrafficPatterns((Boolean) newValue);
        } else if (preference == nightModePreference) {
            sharePrefManager.setNightMode((Boolean) newValue);
            if ((Boolean) newValue) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            if(getActivity() instanceof SettingActivity){
                getActivity().recreate();
            }
        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == cleanCachePreference) {
            SystemUtil.clearAllCache(getActivity());
            SnackBarUtil.show(getView(), "缓存已清除 (*^__^*)");
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