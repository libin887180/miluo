package com.zhongdi.miluo.ui.activity.login;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.OpenAccoutPresenter;
import com.zhongdi.miluo.view.OpenAccountView;

public class OpenAccountActivity extends BaseActivity<OpenAccoutPresenter> implements OpenAccountView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_open_account);
    }



    @Override
    protected OpenAccoutPresenter initPresenter() {
        return new OpenAccoutPresenter(this);
    }

    @Override
    protected void initialize() {

    }
}
