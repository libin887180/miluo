package com.zhongdi.miluo.ui.activity.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.Manager;
import com.zhongdi.miluo.presenter.ForgetPswPresenter;
import com.zhongdi.miluo.view.ForgetPswView;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetDealPswActivity extends BaseActivity<ForgetPswPresenter> implements ForgetPswView {


    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_forget_psw);
    }

    @Override
    public void onSuccess(Manager model) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void enableSubmitBtn(boolean enable) {
        btnSubmit.setEnabled(enable);
    }

    @Override
    protected ForgetPswPresenter initPresenter() {
        return new ForgetPswPresenter(this);
    }

    @Override
    protected void initialize() {
        etNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etNewPassword.getText().length() > 0 && etNewPassword.getText().length() > 0) {
                    enableSubmitBtn(true);
                } else {
                    enableSubmitBtn(false);
                }

            }
        });
    }

    @OnClick({R.id.tv_title_left, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.btn_submit:
                break;
        }
    }
}
