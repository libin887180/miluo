package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.RegistPresenter;
import com.zhongdi.miluo.view.RegistView;
import com.zhongdi.miluo.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegistPresenter> implements RegistView {

    @BindView(R.id.et_username)
    ClearEditText etUsername;
    @BindView(R.id.et_yzm)
    ClearEditText etYzm;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.et_password)
    ClearEditText etPassword;
    @BindView(R.id.btn_regist)
    Button btnRegist;
    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvSendCode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            tvSendCode.setEnabled(true);
            tvSendCode.setText("获取验证码");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_register);
    }

    @Override
    public void openLogin() {

    }

    @Override
    public void onError(String message) {

    }
    @Override
    public void disableLoginBtn() {
        btnRegist.setEnabled(false);
        btnRegist.setTextColor(getResources().getColor(R.color.text_color_light_grey));
    }

    @Override
    public void enableLoginBtn() {
        btnRegist.setEnabled(true);
        btnRegist.setTextColor(Color.WHITE);
    }

    @Override
    public void onSuccess() {
        showToast("注册成功");
        startActivity(new Intent(mContext,RegistSuccessActivity.class));
        finish();
    }

    @Override
    protected RegistPresenter initPresenter() {
        return new RegistPresenter(this);
    }

    @Override
    protected void initialize() {
        disableLoginBtn();
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etUsername.getText().length() > 0 && etPassword.getText().length() > 0&&etYzm.getText().length()>0) {
                    enableLoginBtn();
                } else {
                    disableLoginBtn();
                }

            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etUsername.getText().length() > 0 && etPassword.getText().length() > 0&&etYzm.getText().length()>0) {
                    enableLoginBtn();
                } else {
                    disableLoginBtn();
                }

            }
        });
        etYzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etUsername.getText().length() > 0 && etPassword.getText().length() > 0&&etYzm.getText().length()>0) {
                    enableLoginBtn();
                } else {
                    disableLoginBtn();
                }

            }
        });
    }

    @OnClick({R.id.tv_send_code, R.id.btn_regist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                view.setEnabled(false);
                timer.start();
                break;
            case R.id.btn_regist:
                presenter.regist();
                break;
        }
    }
}
