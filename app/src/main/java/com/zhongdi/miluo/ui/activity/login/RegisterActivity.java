package com.zhongdi.miluo.ui.activity.login;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.RegistPresenter;
import com.zhongdi.miluo.view.RegistView;

public class RegisterActivity extends BaseActivity<RegistPresenter>implements RegistView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_register);
    }

    @Override
    public void openLogin() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    protected RegistPresenter initPresenter() {
        return new RegistPresenter(this);
    }

    @Override
    protected void initialize() {

    }
}
