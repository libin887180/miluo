package com.zhongdi.miluo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.ModifyDealPswPresenter;
import com.zhongdi.miluo.view.ModifyDealPswView;

import butterknife.OnClick;

public class ModifyDealPswActivity extends BaseActivity<ModifyDealPswPresenter> implements ModifyDealPswView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_modify_deal_psw);
    }

    @Override
    protected ModifyDealPswPresenter initPresenter() {
        return new ModifyDealPswPresenter(this);
    }

    @Override
    protected void initialize() {

    }

    @OnClick({R.id.rl_bank_card, R.id.rl_safe_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bank_card:
                break;
            case R.id.rl_safe_center:
                startActivity(new Intent(mContext,SafeCenterActivity.class));
                break;
        }
    }
}
