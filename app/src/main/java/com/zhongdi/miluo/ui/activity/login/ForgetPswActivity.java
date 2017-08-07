package com.zhongdi.miluo.ui.activity.login;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.Manager;
import com.zhongdi.miluo.presenter.ForgetPswPresenter;
import com.zhongdi.miluo.view.ForgetPswView;

public class ForgetPswActivity extends BaseActivity<ForgetPswPresenter> implements ForgetPswView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_find_psw);
    }

    @Override
    public void onSuccess(Manager model) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    protected ForgetPswPresenter initPresenter() {
        return new ForgetPswPresenter(this);
    }

    @Override
    protected void initialize() {

    }
}
