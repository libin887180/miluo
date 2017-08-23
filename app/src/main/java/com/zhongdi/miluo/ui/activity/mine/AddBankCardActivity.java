package com.zhongdi.miluo.ui.activity.mine;

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
import com.zhongdi.miluo.presenter.AddBankCardPresenter;
import com.zhongdi.miluo.view.AddBankCardView;
import com.zhongdi.miluo.widget.ClearEditText;
import com.zhongdi.miluo.widget.CodeAlertDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class AddBankCardActivity extends BaseActivity<AddBankCardPresenter> implements AddBankCardView {

    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.et_bank_card)
    ClearEditText etBankCard;
    @BindView(R.id.et_bank_phone)
    ClearEditText etBankPhone;
    @BindView(R.id.btn_finish)
    Button btnFinish;
     CodeAlertDialog codeAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_add_bank_card);
    }

    @Override
    protected AddBankCardPresenter initPresenter() {
        return new AddBankCardPresenter(this);
    }

    @Override
    protected void initialize() {
        disableNextBtn();
        codeAlertDialog = new CodeAlertDialog(mContext).builder();
        codeAlertDialog.setTitle("短信验证码").setMsg("输入尾号为8888接收到的短信验证码");
        codeAlertDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        codeAlertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeAlertDialog.getTxt_msg().setTextColor(Color.RED);
                codeAlertDialog.getTxt_msg().setText("验证码错误，请重新输入");
            }
        });
        codeAlertDialog.setSendCodeText("发送验证码", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.start();
                view.setEnabled(false);
            }
        });
        etBankCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etBankCard.getText().length() > 0 && etBankPhone.getText().length() > 0) {
                    enableNextBtn();
                } else {
                    disableNextBtn();
                }

            }
        });
        etBankPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etBankCard.getText().length() > 0 && etBankPhone.getText().length() > 0) {
                    enableNextBtn();
                } else {
                    disableNextBtn();
                }

            }
        });
    }

    public void disableNextBtn() {
        btnFinish.setEnabled(false);
        btnFinish.setTextColor(getResources().getColor(R.color.text_color_light_grey));
    }

    public void enableNextBtn() {
        btnFinish.setEnabled(true);
        btnFinish.setTextColor(Color.WHITE);
    }

    @OnClick(R.id.btn_finish)
    public void onViewClicked() {
        codeAlertDialog.show();
    }

    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            codeAlertDialog.getTxt_code().setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            codeAlertDialog.getTxt_code().setEnabled(true);
            codeAlertDialog.getTxt_code().setText("获取验证码");
        }
    };
}
