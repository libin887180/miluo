package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.ForgetPsw1Presenter;
import com.zhongdi.miluo.view.ForgetDealPsw1View;
import com.zhongdi.miluo.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetDealPswActivity1 extends BaseActivity<ForgetPsw1Presenter> implements ForgetDealPsw1View {

    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.et_id_card)
    ClearEditText etIdCard;
    @BindView(R.id.et_real_name)
    ClearEditText etRealName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_forget_deal_psw1);
    }

    @Override
    protected ForgetPsw1Presenter initPresenter() {
        return new ForgetPsw1Presenter(this);
    }

    @Override
    protected void initialize() {
        etIdCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etIdCard.getText().length() > 0 && etRealName.getText().length() > 0) {
                    enableSubmitBtn(true);
                } else {
                    enableSubmitBtn(false);
                }

            }
        });
        etRealName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etIdCard.getText().length() > 0 && etRealName.getText().length() > 0) {
                    enableSubmitBtn(true);
                } else {
                    enableSubmitBtn(false);
                }

            }
        });
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        startActivity(new Intent(mContext,ForgetDealPswActivity2.class));
    }

    @Override
    public void enableSubmitBtn(boolean enable) {
        btnSubmit.setEnabled(enable);
    }

}
