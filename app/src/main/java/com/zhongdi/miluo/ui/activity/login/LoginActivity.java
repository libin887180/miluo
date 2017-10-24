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
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.presenter.LoginPresenter;
import com.zhongdi.miluo.ui.activity.mine.SendCodeActivity;
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
    @BindView(R.id.tv_quick_login)
    TextView tvQuickLogin;
    @BindView(R.id.email_login_form)
    RelativeLayout emailLoginForm;
    private int source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_login);
        source = getIntent().getIntExtra(IntentConfig.SOURCE, -1);
    }


    @OnClick({R.id.btn_login, R.id.tv_title_right, R.id.tv_forget_psw, R.id.tv_quick_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                presenter.login(etUsername.getText().toString(), etPassword.getText().toString(), source);
                break;
            case R.id.tv_title_right://注册到快速登录界面
                finish();
                break;
            case R.id.tv_forget_psw://忘记登录密码
                findPsw();
                break;
            case R.id.tv_quick_login:
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            finish();
        }
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
    public void showErrorPswDialog(int times) {
        showDialog("", "登录密码错误，还剩"+(5-times)+"次机会", "重新输入", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }, "找回密码", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPsw();
            }
        });
    }

    @Override
    public void showLockedDialog() {
        showDialog("", "登录密码已被冻结，请联系客服","联系客服", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showToast("联系客服");
            }
        }, "取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public void openMain() {
        finish();
    }

    @Override
    public void openRegister() {
        startActivity(new Intent(applica, RegisterActivity.class));
    }

    @Override
    public void findPsw() {
        Intent intent_forget = new Intent(mContext, SendCodeActivity.class);
        intent_forget.putExtra(IntentConfig.SOURCE, IntentConfig.FROM_FORGET_PSW);
        startActivity(intent_forget);
    }

    @Override
    public void loginSuccess() {
        setResult(RESULT_OK);
        finish();
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

