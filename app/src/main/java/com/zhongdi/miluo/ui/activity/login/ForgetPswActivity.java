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
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.presenter.ForgetPswPresenter;
import com.zhongdi.miluo.view.ForgetPswView;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPswActivity extends BaseActivity<ForgetPswPresenter> implements ForgetPswView {


    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_new_password2)
    EditText etNewPassword2;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    String userName;
    int from;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("username");
        from = getIntent().getIntExtra("from", -1);
        binding(R.layout.activity_forget_psw);
    }

    @Override
    public void onSuccess() {
        showToast("登录密码设置成功");
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
    protected ForgetPswPresenter initPresenter() {
        return new ForgetPswPresenter(this);
    }

    @Override
    protected void initialize() {
        if (from == IntentConfig.FROM_SET_LOGIN_PSW) {
            title.setText("设置登录密码");
        }
        enableSubmitBtn(false);
        etNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etNewPassword.getText().length() >= 6 && etNewPassword2.getText().length() >= 6) {
                    enableSubmitBtn(true);
                } else {
                    enableSubmitBtn(false);
                }

            }
        });
        etNewPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etNewPassword.getText().length() >= 6 && etNewPassword2.getText().length() >= 6) {
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
                presenter.modifyPsw(userName, etNewPassword.getText().toString(), etNewPassword2.getText().toString());
                break;
        }
    }
}
