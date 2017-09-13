package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.Manager;
import com.zhongdi.miluo.presenter.LoginPresenter;
import com.zhongdi.miluo.ui.activity.MainActivity;
import com.zhongdi.miluo.view.LoginView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_login);
    }


    @OnClick({R.id.btn_login, R.id.tv_title_right, R.id.tv_forget_psw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                openMain();
//                if (presenter.isEmailValid(etUsername.getText().toString())) {
//                    openMain();
//                } else {
//                    showDialog("", "登录密码错误，还剩4次机会", "找回密码", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                        }
//                    }, "重新输入", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                        }
//                    });
//                }
//
//                presenter.login("admin", "123456");

                break;
            case R.id.tv_title_right:
                openRegister();
                break;
            case R.id.tv_forget_psw:
                findPsw();
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
    public void quickLogin() {
        startActivity(new Intent(applica, QuickLoginActivity.class));
    }

    @Override
    public void openMain() {
        startActivity(new Intent(applica, MainActivity.class));
    }

    @Override
    public void openRegister() {
        startActivity(new Intent(applica, RegisterActivity.class));
    }

    @Override
    public void findPsw() {
        startActivity(new Intent(applica, ForgetPswActivity.class));
    }


    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(this);
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

