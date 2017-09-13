package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.SettingPresenter;
import com.zhongdi.miluo.ui.activity.login.TestActivity;
import com.zhongdi.miluo.view.SettingView;

import butterknife.OnClick;

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

    @OnClick({R.id.rl_bank_card, R.id.rl_safe_center,R.id.ll_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bank_card:
                startActivity(new Intent(mContext, BankCardListActivity.class));
                break;
            case R.id.rl_safe_center:
                startActivity(new Intent(mContext, SafeCenterActivity.class));
                break;
            case R.id.ll_test:
                startActivity(new Intent(mContext, TestActivity.class));
                break;
        }
    }
}
