package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.Manager;
import com.zhongdi.miluo.presenter.LoginPresenter;
import com.zhongdi.miluo.ui.activity.MainActivity;
import com.zhongdi.miluo.view.LoginView;
import com.zhongdi.miluo.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
    @BindView(R.id.et_username)
    ClearEditText etUsername;
    @BindView(R.id.et_password)
    ClearEditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.email_login_form)
    RelativeLayout emailLoginForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_login);

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                presenter.login("admin", "123456");
                openMain();
                break;
            case R.id.btn_register:
                openRegister();

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
    public void openMain() {
        startActivity(new Intent(applica, MainActivity.class));
        Toast.makeText(applica, "登录" + etUsername.getText().toString(), Toast
                .LENGTH_SHORT).show();
    }

    @Override
    public void openRegister() {
        startActivity(new Intent(applica, RegisterActivity.class));
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initialize() {

    }
}

