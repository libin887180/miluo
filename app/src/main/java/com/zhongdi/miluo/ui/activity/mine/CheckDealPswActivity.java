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
import com.zhongdi.miluo.base.BaseActivity2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckDealPswActivity extends BaseActivity2 {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.et_deal_password)
    EditText etDealPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_deal_psw);
        ButterKnife.bind(this);
        initialize();
    }


    private void initialize() {
        title.setText("手机号修改");
        disableNextBtn();
        etDealPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etDealPassword.getText().length() > 0 ) {
                    enableNextBtn();
                } else {
                    disableNextBtn();
                }
            }
        });

    }

    public void disableNextBtn() {
        btnSubmit.setEnabled(false);
        btnSubmit.setTextColor(getResources().getColor(R.color.text_color_light_grey));
    }

    public void enableNextBtn() {
        btnSubmit.setEnabled(true);
        btnSubmit.setTextColor(Color.WHITE);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        startActivity(new Intent(mContext, ModifyPhoneNumActivity.class));
        finish();
    }
}
