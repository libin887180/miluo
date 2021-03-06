package com.zhongdi.miluo.ui.activity.market;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.BeforeBuyInfo;
import com.zhongdi.miluo.model.BuyResponse;
import com.zhongdi.miluo.presenter.BuyTiyanjinPresenter;
import com.zhongdi.miluo.ui.activity.login.OpenAccountActivity;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.ui.activity.login.TestActivity;
import com.zhongdi.miluo.ui.activity.mine.SendCodeActivity;
import com.zhongdi.miluo.ui.activity.mine.TransationsRecordActivity;
import com.zhongdi.miluo.view.BuyTiyanjinView;
import com.zhongdi.miluo.widget.AlertDialog;
import com.zhongdi.miluo.widget.ClearEditText;
import com.zhongdi.miluo.widget.OnPasswordInputFinish;
import com.zhongdi.miluo.widget.PayView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static java.lang.Float.parseFloat;

public class BuyTiyanjinActivity extends BaseActivity<BuyTiyanjinPresenter> implements BuyTiyanjinView, View.OnClickListener {

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
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_dep_rate)
    TextView tvDepRate;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.tv_dep_sxf)
    TextView tvDepSxf;
    @BindView(R.id.tv_sxf)
    TextView tvSxf;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.et_money)
    ClearEditText etMoney;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;
    @BindView(R.id.ll_open_account)
    LinearLayout llOpenAccount;
    @BindView(R.id.rl_bank_card)
    RelativeLayout rlBankCard;
    @BindView(R.id.cb_risk)
    CheckBox cbRisk;
    @BindView(R.id.tv_risk)
    TextView tvRisk;
    @BindView(R.id.tv_fund_knowledge)
    TextView tv_fund_knowledge;
    private PopupWindow mPopupWindow;
    private View popView;
    private PayView mPayView;
    private String fundCode;
    private List<BeforeBuyInfo.FeesBean> fees;
    private double minsubscribeamt;
    private BeforeBuyInfo beforeBuyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fundCode = getIntent().getStringExtra("fundCode");
        binding(R.layout.activity_tiyanjin_buy);
    }

    // 显示弹窗
    public void setupPswPopupWindow() {
        // 初始化弹窗
        popView = View.inflate(this, R.layout.pop_window, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popView.findViewById(R.id.gray_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mPayView = (PayView) popView.findViewById(R.id.pv_pop_win);
        mPayView.getTitle().setText("输入六位数字密码");
        // 设置动画
        mPopupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        mPopupWindow.setOutsideTouchable(true);
        mPayView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish() {
                presenter.buyFund(fundCode, mPayView.getPassword(), etMoney.getText().toString());
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
    protected BuyTiyanjinPresenter initPresenter() {
        return new BuyTiyanjinPresenter(this);
    }

    @Override
    protected void initialize() {
        setupPswPopupWindow();
//        setupCardPopupWindow();
        if (SpCacheUtil.getInstance().getUserFundState() == MiluoConfig.UN_OPEN_ACCOUNT) {//未开户
            rlBankCard.setVisibility(View.GONE);
            llOpenAccount.setVisibility(View.VISIBLE);
            etMoney.setEnabled(false);
        } else {
            rlBankCard.setVisibility(View.VISIBLE);
            llOpenAccount.setVisibility(View.GONE);
        }
        tvDepSxf.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        tvDepRate.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        presenter.beforeBuyInit(fundCode);

        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (etMoney.getText().length() > 0 && Double.parseDouble(etMoney.getText().toString()) >= minsubscribeamt) {
                    float amount = parseFloat(etMoney.getText().toString());
                    if (fees != null && fees.size() > 0) {
                        for (int i = 0; i < fees.size(); i++) {
                            if (amount >= fees.get(i).getAmountdownlimit() * 10000) {//没有优惠折扣
                                if (TextUtils.equals(fees.get(i).getDiscount(),"1")) {
                                    tvDepRate.setText("");
                                    tvDepSxf.setText("");
                                    if (parseFloat(fees.get(i).getRatevalue()) > 1) {//达到上限
                                        tvRate.setText(fees.get(i).getRatevalue() + "元");
                                        tvSxf.setText(fees.get(i).getRatevalue() + "元");
                                    } else {//需要乘以费率
                                        tvRate.setText(parseFloat(fees.get(i).getRatevalue()) * 100 + "%");
                                        tvSxf.setText(amount * parseFloat(fees.get(i).getRatevalue()) + "元");
                                    }
                                    break;
                                } else {//有优惠折扣
                                    float rate = parseFloat(fees.get(i).getRatevalue());
                                    float discount = parseFloat(fees.get(i).getDiscount());
                                    if (parseFloat(fees.get(i).getRatevalue()) > 1) {//  达到上限
                                        tvDepRate.setText(rate + "元");
                                        tvRate.setText(rate * discount + "元");
                                        tvSxf.setText(rate * discount + "元");
                                        tvDepSxf.setText(rate + "元");
                                    } else {
                                        tvDepRate.setText(rate * 100 + "%");
                                        tvRate.setText(rate * discount * 100 + "%");//费率*折扣转成%
                                        tvDepSxf.setText(amount * rate + "元");
                                        tvSxf.setText(amount * rate * discount + "元");//金额 *费率*折扣
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (cbAgreement.isChecked() && cbRisk.isChecked()) {
                        enableSubmitBtn();
                    } else {
                        disableSubmitBtn();
                    }
                } else {
                    disableSubmitBtn();
                }
            }
        });

        cbRisk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (etMoney.getText().length() > 0 && Double.parseDouble(etMoney.getText().toString()) >= minsubscribeamt && cbRisk.isChecked() && cbAgreement.isChecked()) {
                    enableSubmitBtn();
                } else {
                    disableSubmitBtn();
                }
            }
        });
        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (etMoney.getText().length() > 0 && Double.parseDouble(etMoney.getText().toString()) >= minsubscribeamt && cbRisk.isChecked() && cbAgreement.isChecked()) {
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
    public void dismissPswPopWindow() {
        mPopupWindow.dismiss();
    }

    @Override
    public void onDataSuccess(BeforeBuyInfo buyInfo) {

//        minsubscribeamt = Float.parseFloat();
        this.beforeBuyInfo = buyInfo;
        String minbuy = buyInfo.getFund().getMinsubscribeamt().substring(0, buyInfo.getFund().getMinsubscribeamt().length() - 2);
        try {
            minsubscribeamt = Double.parseDouble(minbuy);
        } catch (Exception e) {
            minsubscribeamt = 0;
        }

        etMoney.setHint(buyInfo.getFund().getMinsubscribeamt());
        tvFundName.setText(buyInfo.getFund().getFundname());
        tvNum.setText(buyInfo.getFund().getFundcode());
        String fundType;
        switch (buyInfo.getFund().getFundtype()) {
            case MiluoConfig.GUPIAO:
                fundType = "股票型";
                break;
            case MiluoConfig.ZHAIQUAN:
                fundType = "债券型";
                break;
            case MiluoConfig.HUNHE:
                fundType = "混合型";
                break;
            case MiluoConfig.HUOBI:
                fundType = "货币型";
                break;
            case MiluoConfig.ZHISHU:
                fundType = "指数型";
                break;
            case MiluoConfig.BAOBEN:
                fundType = "保本型";
                break;
            case MiluoConfig.ETF:
                fundType = "ETF联接";
                break;
            case MiluoConfig.DQII:
                fundType = "QDII";
                break;
            case MiluoConfig.LOF:
                fundType = "LOF";
                break;
            case MiluoConfig.DUANQI:
                fundType = "短期理财型";
                break;
            case MiluoConfig.ALL:
                fundType = "全部";
                break;
            case MiluoConfig.ZUHE:
                fundType = "组合型";
                break;
            default:
                fundType = "其他";
                break;
        }
        tvFundType.setText(fundType);

//        tvFundType.setText(buyInfo.getFund().getFundtype());
        if (buyInfo.getBankInfo() != null) {//没有银行信息，即没开户
            tvBankName.setText(buyInfo.getBankInfo().getBankname());
            tvDesc.setText(buyInfo.getBankInfo().getAmtdesc());
            Glide.with(mContext).load(buyInfo.getBankInfo().getLogourl()).apply(new RequestOptions().placeholder(R.drawable.icon_bank_default).error(R.drawable.icon_bank_default))
                    .into(ivBankIcon);
        }

        fees = buyInfo.getFees();


    }

    @Override
    public void onBuySuccess(BuyResponse body) {

        SpCacheUtil.getInstance().setTiyanjinStatus("1");/** 0,未参加体验金 1,已参加体坛金*/
        Intent intent = new Intent(mContext, TransationsRecordActivity.class);
        intent.putExtra("tradeid", body.getTradeid() + "");
        intent.putExtra("tradType", "0");//type (integer): 交易类型0申购，1赎回
        intent.putExtra(IntentConfig.SOURCE, "buy");
        intent.putExtra("showriskDialog", true);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void showTestDialog() {
        new AlertDialog(mContext).builder().setMsg("您尚未完成风险测评，请完成测评后继续操作")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).setPositiveButton("立即测评", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TestActivity.class);
                intent.putExtra(IntentConfig.SOURCE, IntentConfig.BUY_FUND);
                startActivityForResult(intent, 101);
            }
        }).show();
    }

    @Override
    public void showReTestDialog() {
        new AlertDialog(mContext).builder().setMsg("您的风险等级为保守型，根据相关法规无法购买当前产品")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).setPositiveButton("重新测评", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TestActivity.class);
                intent.putExtra(IntentConfig.SOURCE, IntentConfig.BUY_FUND);
                startActivityForResult(intent, 103);
            }
        }).show();
    }

    @Override
    public void showRiskTipDialog() {
        new AlertDialog(mContext).builder().setMsg("该产品超过您的风险测评等级，是否仍要购买")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).setPositiveButton("继续购买", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //继续购买
                showPswPopupWindow();
            }
        }).show();
    }

    @Override
    public void showPswLocked() {
        showDialog("", "交易密码已被冻结，请联系客服", "联系客服", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + MiluoConfig.TEL));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, "取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void reLogin() {
        Intent intent = new Intent(mContext, QuickLoginActivity.class);
        startActivityForResult(intent, 301);
    }

    @OnClick({R.id.rl_bank_card, R.id.tv_ld_protocol, R.id.btn_submit, R.id.tv_open_account, R.id.tv_risk, R.id.tv_fund_knowledge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bank_card:
//                showCardPopupWindow();
                break;
            case R.id.tv_fund_knowledge:
                Intent knowledge = new Intent(mContext, HtmlKnowledgeActivity.class);
                startActivity(knowledge);
                break;
            case R.id.tv_ld_protocol:
                Intent proyocol = new Intent(mContext, ProtocolActivity.class);
                startActivity(proyocol);
                break;
            case R.id.tv_risk:
                Intent intent_risk = new Intent(mContext, TestActivity.class);
                intent_risk.putExtra(IntentConfig.SOURCE, IntentConfig.BUY_FUND);
                intent_risk.putExtra(IntentConfig.MAIDIAN, IntentConfig.EX_Gold_Enter_Risk);
                startActivityForResult(intent_risk, 103);
                break;
            case R.id.tv_open_account:
                Intent intent = new Intent(mContext, OpenAccountActivity.class);
                intent.putExtra(IntentConfig.SOURCE, IntentConfig.BUY_FUND);
                intent.putExtra(IntentConfig.MAIDIAN, IntentConfig.EX_Gold_Account);
                startActivityForResult(intent, 102);
                break;
            case R.id.btn_submit:
                if (!cbAgreement.isChecked()) {
                    showToast("请阅读并同意服务协议");
                    return;
                }
                if (!cbRisk.isChecked()) {
                    showToast("请阅读基金小知识或进行风险评测");
                    return;
                }
//                if (SpCacheUtil.getInstance().getUserTestLevel() == -1) {//没有测评
//                    showTestDialog();
//                    return;
//                }
//                if (SpCacheUtil.getInstance().getUserTestLevel() == MiluoConfig.BAOSHOU) {//如果是保守型，并且风险等级比R1高 那重新测评
//                    if (SpCacheUtil.getInstance().getUserTestLevel() < beforeBuyInfo.getFund().getRisklevel()) {
//                        showReTestDialog();
//                        return;
//                    }
//                } else {//如果不是是保守型，并且风险等级比R1高 那提示风险
//                    if (SpCacheUtil.getInstance().getUserTestLevel() < beforeBuyInfo.getFund().getRisklevel()) {
//                        showRiskTipDialog();
//                        return;
//                    }
//                }
//                showTestDialog();
                showPswPopupWindow();
                break;
        }
    }

    private void showPswPopupWindow() {
        setupPswPopupWindow();
        mPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                btnSubmit.performClick();
            }
        }

        if (requestCode == 102) {//开户返回
            if (resultCode == RESULT_OK) {
                //刷新数据
                initialize();
            }
        }

        if (requestCode == 301) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.beforeBuyInit(fundCode);
            } else {
                if(beforeBuyInfo==null) {
                    finish();
                }
            }
        }
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
            case R.id.tv_pop_card_back:
                break;
        }
    }

}
