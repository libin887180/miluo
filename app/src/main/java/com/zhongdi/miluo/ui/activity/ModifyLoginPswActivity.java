package com.zhongdi.miluo.ui.activity;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.ModifyLoginPswPresenter;
import com.zhongdi.miluo.view.ModifyLoginPswView;

public class ModifyLoginPswActivity extends BaseActivity<ModifyLoginPswPresenter> implements ModifyLoginPswView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_modify_deal_psw);
    }

    @Override
    protected ModifyLoginPswPresenter initPresenter() {
        return new ModifyLoginPswPresenter(this);
    }

    @Override
    protected void initialize() {

    }

}
