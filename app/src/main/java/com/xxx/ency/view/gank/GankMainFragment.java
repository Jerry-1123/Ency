package com.xxx.ency.view.gank;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseFragment;
import com.xxx.ency.view.gank.adapter.TitleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xiarh on 2017/11/27.
 */

public class GankMainFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager_gank)
    ViewPager viewPager;

    private List<Fragment> fragments = new ArrayList<>();

    private List<String> types = new ArrayList<>();

    private TitleAdapter titleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initialize() {
        types.add("Android");
        types.add("IOS");
        types.add("前端");
        types.add("拓展资源");
//        types.add("瞎推荐");
//        types.add("福利");
        fragments.add(GankFragment.newInstance("Android"));
        fragments.add(GankFragment.newInstance("iOS"));
        fragments.add(GankFragment.newInstance("前端"));
        fragments.add(GankFragment.newInstance("拓展资源"));
//        fragments.add(GankFragment.newInstance("瞎推荐"));
//        fragments.add(GankFragment.newInstance("福利"));
        titleAdapter = new TitleAdapter(getChildFragmentManager(), fragments, types);
        viewPager.setAdapter(titleAdapter);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setupWithViewPager(viewPager);
    }
}
