package com.zhongdi.miluo.ui.fragment.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.widget.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class OpenStep3Fragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.et_bank_card)
    ClearEditText etBankCard;
    @BindView(R.id.et_bank_phone)
    ClearEditText etBankPhone;
    @BindView(R.id.btn_finish)
    Button btnFinish;

    public static OpenStep3Fragment newInstance(String info) {
        Bundle args = new Bundle();
        OpenStep3Fragment fragment = new OpenStep3Fragment();
        args.putString("index", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_step3, null);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        disableNextBtn();
        return view;
    }

    private void initialize() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
    }


    @OnClick(R.id.btn_finish)
    public void onViewClicked() {
    }
}