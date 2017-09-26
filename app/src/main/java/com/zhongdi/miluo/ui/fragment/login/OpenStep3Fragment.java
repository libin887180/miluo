package com.zhongdi.miluo.ui.fragment.login;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.ui.activity.login.ChooseBankActivity;
import com.zhongdi.miluo.ui.activity.login.OpenAccountActivity;
import com.zhongdi.miluo.util.StringUtil;
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
    @BindView(R.id.tv_band_card_num)
    TextView tvBankCardNum;
    @BindView(R.id.tv_real_name)
    TextView tvRealName;
    @BindView(R.id.et_bank_card)
    ClearEditText etBankCard;
    @BindView(R.id.et_bank_phone)
    ClearEditText etBankPhone;
    @BindView(R.id.btn_finish)
    Button btnFinish;
    OpenAccountActivity parentActivity;
    Unbinder unbinder1;
    private View rootView;

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
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_open_step3, null);
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
        parentActivity = (OpenAccountActivity) getActivity();
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            tvBankCardNum.setText(StringUtil.hindIdcardNum(parentActivity.identityno));
            String name = SpCacheUtil.getInstance().getRealName();
            StringBuffer nameBuffer = new StringBuffer(name);
            nameBuffer.replace(0, 1, "*");
            tvRealName.setText(nameBuffer.toString());
        }
    }

    private void initialize() {

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
        unbinder1.unbind();
    }


    @OnClick({R.id.btn_finish, R.id.rl_bank_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_finish:
                parentActivity.registryid = "0";
                if(StringUtil.isEmpty(parentActivity.bankno)){
                    Toast.makeText(getActivity(), "请选择银行", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(StringUtil.isEmpty(etBankCard.getText().toString())){
                    Toast.makeText(getActivity(), "请输入银行卡号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!StringUtil.isPhoneNum(etBankPhone.getText().toString())){
                    Toast.makeText(getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                parentActivity.bankcardno = etBankCard.getText().toString();
                parentActivity.phone = etBankCard.getText().toString();
                parentActivity.openAccount();
                break;
            case R.id.rl_bank_card:
                startActivityForResult(new Intent(getActivity(), ChooseBankActivity.class), 101);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
              String bankno =  data.getStringExtra("bankno");
                parentActivity.bankno = bankno;
            }
        }
    }
}