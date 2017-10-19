package com.zhongdi.miluo.ui.fragment.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private View rootView;

    public static OpenStep2Fragment newInstance(String info) {
        Bundle args = new Bundle();
        OpenStep2Fragment fragment = new OpenStep2Fragment();
        args.putString("index", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_open_step2, null);
            unbinder = ButterKnife.bind(this, rootView);
            initialize();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，
            // 要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    private void initialize() {
        disableNextBtn();
        etPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPassword1.getText().length() ==6 && etPassword2.getText().length() ==6) {
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
                if (etPassword1.getText().length() ==6 && etPassword2.getText().length() ==6) {
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
        if(!TextUtils.equals(etPassword1.getText().toString(),etPassword2.getText().toString())){
            Toast.makeText(getActivity(), "两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        OpenAccountActivity activity = (OpenAccountActivity) getActivity();
        activity.tradepwd = etPassword1.getText().toString();
        activity.swapViewPagerTo(2);
    }
}