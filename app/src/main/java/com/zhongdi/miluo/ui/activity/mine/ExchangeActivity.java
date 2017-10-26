package com.zhongdi.miluo.ui.activity.mine;

import android.os.Bundle;
import android.view.View;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.ExchangePresenter;
import com.zhongdi.miluo.view.ExchangeView;
import com.zhongdi.miluo.widget.ClearEditText;
import com.zhongdi.miluo.widget.ExchangeAlertDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ExchangeActivity extends BaseActivity<ExchangePresenter> implements ExchangeView {

    @BindView(R.id.et_phone)
    ClearEditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_exchange);
    }

    @Override
    protected ExchangePresenter initPresenter() {
        return new ExchangePresenter(this);
    }

    @Override
    protected void initialize() {

    }


    @Override
    public void onDataSuccess(List<String> body) {

    }

    @Override
    public void dismissLoadingDialog() {
        getLoadingProgressDialog().dismiss();
    }

    @Override
    public void showLoadingDialog() {
        getLoadingProgressDialog().show();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        new ExchangeAlertDialog(mContext).builder().setMsg("话费兑换成功")
                .setPositiveButton("更多好基", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();

    }
}
