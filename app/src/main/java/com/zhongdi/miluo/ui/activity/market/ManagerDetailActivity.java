package com.zhongdi.miluo.ui.activity.market;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.ManagerDetailPresenter;
import com.zhongdi.miluo.view.ManagerDetailView;

public class ManagerDetailActivity extends BaseActivity<ManagerDetailPresenter> implements ManagerDetailView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_manager_detail);
    }

    @Override
    protected ManagerDetailPresenter initPresenter() {
        return new ManagerDetailPresenter(this);
    }

    @Override
    protected void initialize() {


    }

}
