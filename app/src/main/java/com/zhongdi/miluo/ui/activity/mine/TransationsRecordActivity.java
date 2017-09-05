package com.zhongdi.miluo.ui.activity.mine;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.TransactionRecordPresenter;
import com.zhongdi.miluo.view.TransactionRecordView;

public class TransationsRecordActivity extends BaseActivity<TransactionRecordPresenter> implements TransactionRecordView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_transaction_record);
    }

    @Override
    protected TransactionRecordPresenter initPresenter() {
        return new TransactionRecordPresenter(this);
    }

    @Override
    protected void initialize() {

    }
}
