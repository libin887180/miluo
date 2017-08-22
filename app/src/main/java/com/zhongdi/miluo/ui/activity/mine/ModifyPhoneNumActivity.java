package com.zhongdi.miluo.ui.activity.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.ModifyPhoneNumPresenter;
import com.zhongdi.miluo.view.ModifyPhoneNumView;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyPhoneNumActivity extends BaseActivity<ModifyPhoneNumPresenter> implements ModifyPhoneNumView {

    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_modify_phone_num);
    }

    @Override
    protected ModifyPhoneNumPresenter initPresenter() {
        return new ModifyPhoneNumPresenter(this);
    }

    @Override
    protected void initialize() {
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
                if (etTel.getText().length() > 0 ) {
                    enableNextBtn();
                } else {
                    disableNextBtn();
                }
            }
        });

    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        showToast("修改成功");
        finish();
    }

    public void disableNextBtn() {
        btnSubmit.setEnabled(false);
        btnSubmit.setTextColor(getResources().getColor(R.color.text_color_light_grey));
    }

    public void enableNextBtn() {
        btnSubmit.setEnabled(true);
        btnSubmit.setTextColor(Color.WHITE);
    }
}
