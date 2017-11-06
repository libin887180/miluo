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
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.model.UserInfo;
import com.zhongdi.miluo.presenter.TiyanjinQuickLoginPresenter;
import com.zhongdi.miluo.util.StringUtil;
import com.zhongdi.miluo.view.TiyanjinQuickLoginView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A login screen that offers login via email/password.
 */
public class TiyanjinLoginActivity extends BaseActivity<TiyanjinQuickLoginPresenter> implements TiyanjinQuickLoginView {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    private int source;
    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvSendCode.setText("验证码(" + millisUntilFinished / 1000 + "S)");
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
        binding(R.layout.activity_tiyanjin_quick_login);
        source = getIntent().getIntExtra(IntentConfig.SOURCE, -1);
    }

    @OnClick({R.id.btn_login, R.id.tv_send_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                presenter.login(etUsername.getText().toString(), etCode.getText().toString(), source);
                break;
            case R.id.tv_send_code:
                if (!StringUtil.isPhoneNum(etUsername.getText().toString())) {
                    showToast("请输入正确格式的手机号");
                    return;
                }
                presenter.sendMessage(etUsername.getText().toString());
                view.setEnabled(false);
                timer.start();
                break;
        }
    }

    @Override
    public void loginSuccess(UserInfo userInfo) {
        Intent intent = new Intent(mContext, TiyanjinInfoActivity.class);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void disableLoginBtn() {
        btnLogin.setEnabled(false);
//        btnLogin.setTextColor(getResources().getColor(R.color.text_color_light_grey));
    }

    @Override
    public void enableLoginBtn() {
        btnLogin.setEnabled(true);
        btnLogin.setTextColor(Color.WHITE);
    }


    @Override
    protected TiyanjinQuickLoginPresenter initPresenter() {
        return new TiyanjinQuickLoginPresenter(this);
    }

    @Override
    protected void initialize() {
//        disableLoginBtn();
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if (etUsername.getText().length() == 11) {
//                    tvSendCode.setEnabled(true);
//                } else {
//                    tvSendCode.setEnabled(false);
//                }

            }
        });
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if (etUsername.getText().length() > 0 && etCode.getText().length() > 0) {
//                    enableLoginBtn();
//                } else {
//                    disableLoginBtn();
//                }

            }
        });
    }
}

