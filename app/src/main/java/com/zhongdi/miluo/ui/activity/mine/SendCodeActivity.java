package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.SendCoderesenter;
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
    private int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        from = getIntent().getIntExtra("from", 0);
        binding(R.layout.activity_send_code);

    }

    @Override
    protected SendCoderesenter initPresenter() {
        return new SendCoderesenter(this);
    }

    @Override
    protected void initialize() {
        if (from == 0) {
            title.setText("登录密码修改");
        } else {
            title.setText("交易密码修改");
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

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        if (from == 0) {
            startActivity(new Intent(mContext, ModifyLoginPswActivity.class));
        } else {
            startActivity(new Intent(mContext, ModifyDealPswActivity.class));
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

}
