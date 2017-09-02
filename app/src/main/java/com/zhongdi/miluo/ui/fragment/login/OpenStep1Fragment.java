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

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.ui.activity.login.OpenAccountActivity;
import com.zhongdi.miluo.widget.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class OpenStep1Fragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.et_phnoe)
    ClearEditText etPhnoe;
    @BindView(R.id.et_id_card)
    ClearEditText etIdCard;
    @BindView(R.id.btn_next)
    Button btnNext;

    public static OpenStep1Fragment newInstance(String info) {
        Bundle args = new Bundle();
        OpenStep1Fragment fragment = new OpenStep1Fragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_step1, null);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        disableNextBtn();
        return view;
    }

    private void initialize() {
        etPhnoe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPhnoe.getText().length() > 0 && etIdCard.getText().length() > 0) {
                    enableNextBtn();
                } else {
                    disableNextBtn();
                }

            }
        });
        etIdCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPhnoe.getText().length() > 0 && etIdCard.getText().length() > 0) {
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
        if(unbinder!=null &&unbinder != Unbinder.EMPTY){
            unbinder.unbind();
        }
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        OpenAccountActivity activity = (OpenAccountActivity) getActivity();
        activity.swapViewPagerTo(1);
    }
}