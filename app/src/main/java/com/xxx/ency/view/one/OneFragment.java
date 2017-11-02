package com.xxx.ency.view.one;

import android.widget.TextView;

import com.xxx.ency.R;
import com.xxx.ency.base.BaseMVPFragment;
import com.xxx.ency.config.Constants;
import com.xxx.ency.config.EncyApplication;
import com.xxx.ency.contract.OneContract;
import com.xxx.ency.di.component.DaggerFragmentComponent;
import com.xxx.ency.di.module.OneFragmentModule;
import com.xxx.ency.model.bean.OneBean;
import com.xxx.ency.presenter.OnePresenter;

import butterknife.BindView;

/**
 * Created by xiarh on 2017/11/1.
 */

public class OneFragment extends BaseMVPFragment<OnePresenter> implements OneContract.View {

    @BindView(R.id.txt)
    TextView txt;

    @Override
    protected void initInject() {
        DaggerFragmentComponent.builder()
                .appComponent(EncyApplication.getAppComponent())
                .oneFragmentModule(new OneFragmentModule())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initialize() {
        mPresenter.getData(Constants.ONE_URL);
    }

    @Override
    public void showOneBean(OneBean oneBean) {
        txt.setText(oneBean.getTitle());
    }
}
