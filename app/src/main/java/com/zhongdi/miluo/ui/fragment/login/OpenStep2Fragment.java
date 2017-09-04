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
import android.widget.EditText;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.ui.activity.login.OpenAccountActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class OpenStep2Fragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.et_password1)
    EditText etPassword1;
    @BindView(R.id.et_password2)
    EditText etPassword2;

    public static OpenStep2Fragment newInstance(String info) {
        Bundle args = new Bundle();
        OpenStep2Fragment fragment = new OpenStep2Fragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_step2, null);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        disableNextBtn();
        return view;
    }

    private void initialize() {
        etPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPassword1.getText().length() > 0 && etPassword2.getText().length() > 0) {
                    enableNextBtn();
                } else {
                    disableNextBtn();
                }

            }
        });
        etPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPassword1.getText().length() > 0 && etPassword2.getText().length() > 0) {
                    enableNextBtn();
                } else {
                    disableNextBtn();
                }

            }
        });
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
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        OpenAccountActivity activity = (OpenAccountActivity) getActivity();
        activity.swapViewPagerTo(2);
    }
}