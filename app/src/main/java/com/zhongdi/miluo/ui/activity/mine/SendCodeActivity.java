package com.zhongdi.miluo.ui.activity.mine;

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
import com.zhongdi.miluo.presenter.SendCoderesenter;
import com.zhongdi.miluo.ui.activity.login.ForgetDealPswActivity1;
import com.zhongdi.miluo.ui.activity.login.ForgetPswActivity;
import com.zhongdi.miluo.util.StringUtil;
import com.zhongdi.miluo.view.SendCodeView;

import butterknife.BindView;
import butterknife.OnClick;

public class SendCodeActivity extends BaseActivity<SendCoderesenter> implements SendCodeView {

    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    private int from;
    private String sendCodeType;//验证码类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        from = getIntent().getIntExtra(IntentConfig.SOURCE, 0);
        if (from == IntentConfig.FROM_MODIFY_PSW || from == IntentConfig.FROM_MODIFY_DEAL_PSW) {
            binding(R.layout.activity_send_code);
        } else {
            binding(R.layout.activity_send_code2);
        }

    }

    @Override
    protected SendCoderesenter initPresenter() {
        return new SendCoderesenter(this);
    }

    @Override
    protected void initialize() {

        if (from == IntentConfig.FROM_MODIFY_PSW) {
            title.setText("登录密码修改");
            sendCodeType = "2";
        } else if (from == IntentConfig.FROM_MODIFY_DEAL_PSW) {
            title.setText("交易密码修改");
            sendCodeType = "4";
        } else if (from == IntentConfig.FROM_FORGET_PSW) {
            title.setText("忘记登录密码");
            sendCodeType = "3";
        } else if (from == IntentConfig.FROM_FORGET_DEAL_PSW) {
            title.setText("忘记交易密码");
            sendCodeType = "5";
        } else if (from == IntentConfig.FROM_SET_LOGIN_PSW) {
            title.setText("设置登录密码");
            sendCodeType = "2";
        }
        disableNextBtn();
        etTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etTel.getText().length() > 0 && etCode.getText().length() > 0) {
                    enableNextBtn();
                } else {
                    disableNextBtn();
                }

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
                if (etTel.getText().length() > 0 && etCode.getText().length() > 0) {
                    enableNextBtn();
                } else {
                    disableNextBtn();
                }

            }
        });
    }

    @OnClick({R.id.btn_next, R.id.tv_send_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                if (!StringUtil.isPhoneNum(etTel.getText().toString())) {
                    showToast("请输入正确格式的手机号");
                    return;
                }
                view.setEnabled(false);
                timer.start();
                presenter.sendMessage(etTel.getText().toString(), sendCodeType);
                break;
            case R.id.btn_next:
                presenter.checkCode(etTel.getText().toString(), etCode.getText().toString(), sendCodeType);
                break;
        }


    }

    public void disableNextBtn() {
        btnNext.setEnabled(false);
        btnNext.setTextColor(getResources().getColor(R.color.text_color_light_grey));
    }

    public void enableNextBtn() {
        btnNext.setEnabled(true);
        btnNext.setTextColor(Color.WHITE);
    }

    @Override
    public void toNext() {
        if (!StringUtil.isPhoneNum(etTel.getText().toString())) {
            showToast("请输入正确格式的手机号码");
            return;
        }
        if (from == IntentConfig.FROM_MODIFY_PSW) {
            Intent intent = new Intent(mContext, ModifyLoginPswActivity.class);
            intent.putExtra("username", etTel.getText().toString());
            startActivity(intent);
        } else if (from == IntentConfig.FROM_MODIFY_DEAL_PSW) {
            Intent intent = new Intent(mContext, ModifyDealPswActivity.class);
            intent.putExtra("username", etTel.getText().toString());
            startActivity(intent);
        } else if (from == IntentConfig.FROM_FORGET_PSW) {
            Intent intent = new Intent(mContext, ForgetPswActivity.class);
            intent.putExtra("username", etTel.getText().toString());
            startActivity(intent);
        } else if (from == IntentConfig.FROM_FORGET_DEAL_PSW) {
            Intent intent = new Intent(mContext, ForgetDealPswActivity1.class);
            intent.putExtra("username", etTel.getText().toString());
            startActivity(intent);
        } else if (from == IntentConfig.FROM_SET_LOGIN_PSW) {
            Intent intent = new Intent(mContext, ForgetPswActivity.class);
            intent.putExtra("username", etTel.getText().toString());
            intent.putExtra("from", IntentConfig.FROM_SET_LOGIN_PSW);
            startActivity(intent);
        }
        finish();
    }

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
}
