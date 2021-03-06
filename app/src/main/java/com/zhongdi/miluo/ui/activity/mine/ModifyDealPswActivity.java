package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.ModifyDealPswPresenter;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.view.ModifyDealPswView;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyDealPswActivity extends BaseActivity<ModifyDealPswPresenter> implements ModifyDealPswView {

    @BindView(R.id.et_orginal_password)
    EditText etOrginalPassword;
    @BindView(R.id.et_password1)
    EditText etPassword1;
    @BindView(R.id.et_password2)
    EditText etPassword2;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_modify_deal_psw);
    }

    @Override
    protected ModifyDealPswPresenter initPresenter() {
        return new ModifyDealPswPresenter(this);
    }

    @Override
    protected void initialize() {
        enableSubmitBtn(false);
        etOrginalPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPassword1.getText().length() >= 6 && etPassword2.getText().length() >= 6
                        && etOrginalPassword.getText().length() >= 6) {
                    enableSubmitBtn(true);
                } else {
                    enableSubmitBtn(false);
                }

            }
        });
        etPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPassword1.getText().length() >= 6 && etPassword2.getText().length() >= 6
                        && etOrginalPassword.getText().length() >= 6) {
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
                if (etPassword1.getText().length() >= 6 && etPassword2.getText().length() >= 6
                        && etOrginalPassword.getText().length() >= 6) {
                    enableSubmitBtn(true);
                } else {
                    enableSubmitBtn(false);
                }

            }
        });
    }

    @Override
    public void onSuccess() {
        showToast("密码修改成功");
        finish();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void enableSubmitBtn(boolean enable) {
        btnSubmit.setEnabled(enable);
    }

    @Override
    public void reLogin() {
        Intent intent  = new Intent(mContext, QuickLoginActivity.class);
        startActivityForResult(intent, 301);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        presenter.modifyPsw(etOrginalPassword.getText().toString(), etPassword1.getText().toString(), etPassword2.getText().toString());
    }
}
