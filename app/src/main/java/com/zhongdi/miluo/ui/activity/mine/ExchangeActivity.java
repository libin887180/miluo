package com.zhongdi.miluo.ui.activity.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.Prize;
import com.zhongdi.miluo.presenter.ExchangePresenter;
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
    private Prize prize;
    ExchangeAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prize = (Prize) getIntent().getSerializableExtra("prize");
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

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        presenter.exchange(prize.getAmount(), prize.getId(), prize.getType(), etPhone.getText().toString());
    }
}
