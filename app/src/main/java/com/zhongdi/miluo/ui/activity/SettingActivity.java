package com.zhongdi.miluo.ui.activity;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.SettingPresenter;
import com.zhongdi.miluo.view.SettingView;

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_setting);
    }

    @Override
    protected SettingPresenter initPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected void initialize() {

    }
}
