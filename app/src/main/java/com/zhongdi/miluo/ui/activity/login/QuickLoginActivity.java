package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.Manager;
import com.zhongdi.miluo.presenter.QuickLoginPresenter;
import com.zhongdi.miluo.ui.activity.MainActivity;
import com.zhongdi.miluo.view.QuickLoginView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A login screen that offers login via email/password.
 */
public class QuickLoginActivity extends BaseActivity<QuickLoginPresenter> implements QuickLoginView {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_title_right)
    TextView btnRegister;
    @BindView(R.id.email_login_form)
    RelativeLayout emailLoginForm;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;

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
        binding(R.layout.activity_quick_login);
    }

    @OnClick({R.id.btn_login, R.id.tv_title_right, R.id.tv_forget_psw, R.id.tv_send_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                presenter.login("admin", "123456");
                openMain();
                break;
            case R.id.tv_title_right:
                break;
            case R.id.tv_forget_psw:
                break;
            case R.id.tv_send_code:
                view.setEnabled(false);
                timer.start();

                break;
        }
    }

    @Override
    public void onSuccess(Manager model) {

    }

    @Override
    public void onError(String message) {
        showToast(message);
    }

    @Override
    public void disableLoginBtn() {
        btnLogin.setEnabled(false);
        btnLogin.setTextColor(getResources().getColor(R.color.text_color_light_grey));
    }

    @Override
    public void enableLoginBtn() {
        btnLogin.setEnabled(true);
        btnLogin.setTextColor(Color.WHITE);
    }

    @Override
    public void openMain() {
        startActivity(new Intent(applica, MainActivity.class));
        Toast.makeText(applica, "登录" + etUsername.getText().toString(), Toast
                .LENGTH_SHORT).show();
    }


    @Override
    protected QuickLoginPresenter initPresenter() {
        return new QuickLoginPresenter(this);
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
                if (etUsername.getText().length() > 0 && etPassword.getText().length() > 0) {
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
                if (etUsername.getText().length() > 0 && etPassword.getText().length() > 0) {
                    enableLoginBtn();
                } else {
                    disableLoginBtn();
                }

            }
        });
    }
}

