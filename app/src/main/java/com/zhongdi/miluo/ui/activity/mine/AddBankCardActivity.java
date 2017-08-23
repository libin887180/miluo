package com.zhongdi.miluo.ui.activity.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.AddBankCardPresenter;
import com.zhongdi.miluo.view.AddBankCardView;
import com.zhongdi.miluo.widget.ClearEditText;

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
    }
}
