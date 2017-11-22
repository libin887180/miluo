package com.zhongdi.miluo.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.ExchangePresenter;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.view.ExchangeView;
import com.zhongdi.miluo.widget.ClearEditText;
import com.zhongdi.miluo.widget.ExchangeAlertDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ExchangeActivity extends BaseActivity<ExchangePresenter> implements ExchangeView {

    @BindView(R.id.et_phone)
    ClearEditText etPhone;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private String prizeId;
    private String prizeType;
    ExchangeAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prizeId = getIntent().getStringExtra("prizeId");
        prizeType = getIntent().getStringExtra("prizeType");
        binding(R.layout.activity_exchange);
    }

    @Override
    protected ExchangePresenter initPresenter() {
        return new ExchangePresenter(this);
    }

    @Override
    protected void initialize() {
        btnSubmit.setEnabled(false);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPhone.getText().length() == 11) {
                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }
            }
        });
    }


    @Override
    public void onDataSuccess() {
        dialog = new ExchangeAlertDialog(mContext).builder().setMsg("话费兑换成功")
                .setPositiveButton("更多好基", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        getLoadingProgressDialog().dismiss();
    }

    @Override
    public void showLoadingDialog() {
        getLoadingProgressDialog().show();
    }


    @Override
    public void reLogin() {
        Intent intent  = new Intent(mContext, QuickLoginActivity.class);
        startActivityForResult(intent, 301);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 301 && resultCode == Activity.RESULT_OK) {
        }
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        presenter.exchange(prizeId, prizeType, etPhone.getText().toString());
    }
}
