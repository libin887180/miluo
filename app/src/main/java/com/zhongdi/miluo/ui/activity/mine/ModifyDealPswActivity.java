package com.zhongdi.miluo.ui.activity.mine;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.ModifyDealPswPresenter;
import com.zhongdi.miluo.view.ModifyDealPswView;

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

}
