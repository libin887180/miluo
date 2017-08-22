package com.zhongdi.miluo.ui.activity.mine;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.BankCardListPresenter;
import com.zhongdi.miluo.view.BankCardListView;

public class BankCardListActivity extends BaseActivity<BankCardListPresenter> implements BankCardListView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_setting);
    }

    @Override
    protected BankCardListPresenter initPresenter() {
        return new BankCardListPresenter(this);
    }

    @Override
    protected void initialize() {


    }
}
