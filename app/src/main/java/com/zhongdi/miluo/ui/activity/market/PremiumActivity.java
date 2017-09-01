package com.zhongdi.miluo.ui.activity.market;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.PremiumPresenter;
import com.zhongdi.miluo.view.PremiumView;

public class PremiumActivity extends BaseActivity<PremiumPresenter> implements PremiumView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_premium_rate);
    }

    @Override
    protected PremiumPresenter initPresenter() {
        return new PremiumPresenter(this);
    }

    @Override
    protected void initialize() {
    }

}
