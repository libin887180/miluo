package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.presenter.ForgetDealPsw2Presenter;
import com.zhongdi.miluo.view.ForgetDealPsw2View;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetDealPswActivity2 extends BaseActivity<ForgetDealPsw2Presenter> implements ForgetDealPsw2View {

    @BindView(R.id.et_password1)
    EditText etPassword1;
    @BindView(R.id.et_password2)
    EditText etPassword2;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_forget_deal_psw2);
    }

    @Override
    protected ForgetDealPsw2Presenter initPresenter() {
        return new ForgetDealPsw2Presenter(this);
    }

    @Override
    protected void initialize() {
        etPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPassword1.getText().length() == 6 && etPassword2.getText().length() == 6) {
                    enableSubmitBtn(true);
                } else {
                    enableSubmitBtn(false);
                }

            }
        });
        etPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPassword1.getText().length() == 6 && etPassword2.getText().length() == 6) {
                    enableSubmitBtn(true);
                } else {
                    enableSubmitBtn(false);
                }

            }
        });
    }

    @Override
    public void enableSubmitBtn(boolean enable) {
        btnSubmit.setEnabled(enable);
    }

    @Override
    public void onSuccess() {
        showToast("新交易密码设置成功");
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void reLogin() {
        Intent intent  = new Intent(mContext, QuickLoginActivity.class);
        startActivityForResult(intent, 301);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
//重置交易 密码
        presenter.modifyDealPsw(SpCacheUtil.getInstance().getLoginAccount(),
                etPassword1.getText().toString(), etPassword2.getText().toString());
    }
}
