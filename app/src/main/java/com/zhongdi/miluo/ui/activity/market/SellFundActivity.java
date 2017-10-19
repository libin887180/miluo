package com.zhongdi.miluo.ui.activity.market;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.model.BuyResponse;
import com.zhongdi.miluo.model.SellResponse;
import com.zhongdi.miluo.presenter.SellFundPresenter;
import com.zhongdi.miluo.ui.activity.mine.SendCodeActivity;
import com.zhongdi.miluo.ui.activity.mine.TransationsRecordActivity;
import com.zhongdi.miluo.view.SellFundView;
import com.zhongdi.miluo.widget.ClearEditText;
import com.zhongdi.miluo.widget.OnPasswordInputFinish;
import com.zhongdi.miluo.widget.PayView;

import butterknife.BindView;
import butterknife.OnClick;

public class SellFundActivity extends BaseActivity<SellFundPresenter> implements SellFundView, View.OnClickListener {

    @BindView(R.id.et_money)
    ClearEditText etMoney;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_fund_name)
    TextView tvFundName;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_fund_type)
    TextView tvFundType;
    @BindView(R.id.iv_bank_icon)
    ImageView ivBankIcon;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_last_num)
    TextView tvLastNum;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_sell_all)
    TextView tvSellAll;
    @BindView(R.id.tv_accounting_date)
    TextView tvAccountingDate;
    private PopupWindow mPopupWindow;
    private View popView;
    private PayView mPayView;
    private String fundCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fundCode = getIntent().getStringExtra("fundCode");
        binding(R.layout.activity_sell);
    }

    // 显示弹窗
    public void setupPswPopupWindow() {
        // 初始化弹窗
        popView = View.inflate(this, R.layout.pop_window, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPayView = (PayView) popView.findViewById(R.id.pv_pop_win);
        mPayView.getTitle().setText("输入六位数字密码");
        // 设置动画
        mPopupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        // mPopupWindow.showAsDropDown(findViewById(R.id.head), 0, 0);
        mPopupWindow.setOutsideTouchable(true);
        mPayView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish() {
                presenter.sellFund(fundCode, mPayView.getPassword(), etMoney.getText().toString());
                dismissPswPopWindow();
            }
        });
        mPayView.getCancel().setOnClickListener(this);
        mPayView.getForgetPsw().setOnClickListener(this);
    }

    @Override
    public void dismissLoadingDialog() {
        getLoadingProgressDialog().dismiss();
    }

    @Override
    public void showLoadingDialog() {
        getLoadingProgressDialog().show();
    }

    @Override
    public void onSellSuccess(BuyResponse body) {
        Intent intent = new Intent(mContext, TransationsRecordActivity.class);
        intent.putExtra("tradeid", body.getTradeid() + "");
        intent.putExtra("tradType", "1");//type (integer): 交易类型0申购，1赎回
        intent.putExtra(IntentConfig.SOURCE,"sell");
        startActivity(intent);
        finish();
    }


    @Override
    protected SellFundPresenter initPresenter() {
        return new SellFundPresenter(this);
    }

    @Override
    protected void initialize() {
        setupPswPopupWindow();
        presenter.beforeSellInit(fundCode);
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etMoney.getText().length() > 0) {
                    enableSubmitBtn();
                } else {
                    disableSubmitBtn();
                }

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPopupWindow != null && mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void disableSubmitBtn() {
        btnSubmit.setEnabled(false);
    }

    @Override
    public void enableSubmitBtn() {
        btnSubmit.setEnabled(true);
    }

    @Override
    public void onDataSuccess(SellResponse body) {

        etMoney.setHint("最低可赎回"+body.getFund().getMinredemptionvol()+"份");
        tvFundName.setText(body.getFund().getFundname());
        tvNum.setText(body.getFund().getFundcode());
        tvFundType.setText(body.getFund().getFundtype());
        if (body.getBankInfo() != null) {//没有银行信息，即没开户
            tvBankName.setText(body.getBankInfo().getBankname());
            tvLastNum.setText(body.getBankInfo().getCard());
            Glide.with(mContext).load(body.getBankInfo().getLogourl()).apply(new RequestOptions().placeholder(R.drawable.icon_bank_default).error(R.drawable.icon_bank_default))
                    .into(ivBankIcon);

        }
        tvTotal.setText(body.getFund().getAvaliableshare());
        tvAccountingDate.setText(body.getFund().getPreredeemacctdate());

    }

    @Override
    public void dismissPswPopWindow() {
        mPopupWindow.dismiss();
    }

    @OnClick({R.id.btn_submit, R.id.tv_sell_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sell_all:
                etMoney.setText(tvTotal.getText());
                break;
            case R.id.btn_submit:
                showPswPopupWindow();
                break;
        }
    }

    private void showPswPopupWindow() {
        setupPswPopupWindow();
        mPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pay_back:
                dismissPswPopWindow();
                break;
            case R.id.tv_pay_forgetPwd:
                Intent intent_forget = new Intent(mContext, SendCodeActivity.class);
                intent_forget.putExtra(IntentConfig.SOURCE, IntentConfig.FROM_FORGET_DEAL_PSW);//来自忘记交易密码
                startActivity(intent_forget);
                mPayView.clearPassword();
                dismissPswPopWindow();
                break;
        }
    }

}
