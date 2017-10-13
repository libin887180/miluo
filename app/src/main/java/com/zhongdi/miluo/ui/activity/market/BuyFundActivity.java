package com.zhongdi.miluo.ui.activity.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BankListAdapter;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.BankInfo;
import com.zhongdi.miluo.model.BeforeBuyInfo;
import com.zhongdi.miluo.model.BuyResponse;
import com.zhongdi.miluo.presenter.BuyFundPresenter;
import com.zhongdi.miluo.ui.activity.mine.TransationsRecordActivity;
import com.zhongdi.miluo.view.BuyFundView;
import com.zhongdi.miluo.widget.ClearEditText;
import com.zhongdi.miluo.widget.OnPasswordInputFinish;
import com.zhongdi.miluo.widget.PayView;
import com.zhongdi.miluo.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static java.lang.Float.parseFloat;

public class BuyFundActivity extends BaseActivity<BuyFundPresenter> implements BuyFundView, View.OnClickListener {

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
    private PopupWindow mPopupWindow;
    private View popView;
    private PayView mPayView;
    private RecyclerView recyclerView;
    private BankListAdapter listAdapter;
    private PopupWindow mCardPopupWindow;
    private View cardPopView;
    private String fundCode;
    private List<BeforeBuyInfo.FeesBean> fees;
    private float minsubscribeamt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fundCode = getIntent().getStringExtra("fundCode");
        binding(R.layout.activity_buy);
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
//                startActivity(new Intent(mContext, TransationsRecordActivity.class));
//                Toast.makeText(mContext, mPayView.getPassword(), Toast.LENGTH_SHORT).show();
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

    // 显示银行卡弹窗
    public void setupCardPopupWindow() {
        // 初始化弹窗
        cardPopView = View.inflate(this, R.layout.pop_card_list_view, null);
        mCardPopupWindow = new PopupWindow(cardPopView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        cardPopView.findViewById(R.id.gray_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardPopupWindow.dismiss();
            }
        });
        cardPopView.findViewById(R.id.tv_pop_card_back).setOnClickListener(this);
        recyclerView = (RecyclerView) cardPopView.findViewById(R.id.rl_card_list);
        List<BankInfo> datas = new ArrayList<>();
        datas.add(new BankInfo());
        datas.add(new BankInfo());
        datas.add(new BankInfo());
        listAdapter = new BankListAdapter(mContext, datas);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(listAdapter);
        listAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                mCardPopupWindow.dismiss();
            }
        });
        // 设置动画
        mCardPopupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        // mPopupWindow.showAsDropDown(findViewById(R.id.head), 0, 0);
        mCardPopupWindow.setOutsideTouchable(true);
    }

    @Override
    protected BuyFundPresenter initPresenter() {
        return new BuyFundPresenter(this);
    }

    @Override
    protected void initialize() {
        setupPswPopupWindow();
//        setupCardPopupWindow();
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

                if (etMoney.getText().length() > 0) {
                    float amount = parseFloat(etMoney.getText().toString());
                    for (int i = 0; i < fees.size(); i++) {
                        if (amount >= fees.get(i).getAmountdownlimit() * 10000) {//没有优惠折扣
                            if (fees.get(i).getDiscount().equals("1")) {
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
            if (mCardPopupWindow != null && mCardPopupWindow.isShowing()) {
                mCardPopupWindow.dismiss();
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
        etMoney.setHint(buyInfo.getFund().getMinsubscribeamt());
        tvFundName.setText(buyInfo.getFund().getFundname());
        tvNum.setText(buyInfo.getFund().getFundcode());
        tvFundType.setText(buyInfo.getFund().getFundtype());
        tvBankName.setText(buyInfo.getBankInfo().getBankname());
        tvDesc.setText(buyInfo.getBankInfo().getAmtdesc());
        fees = buyInfo.getFees();
        Glide.with(mContext).load(buyInfo.getBankInfo().getLogourl()).apply(new RequestOptions().placeholder(R.drawable.icon_bank_default).error(R.drawable.icon_bank_default))
                .into(ivBankIcon);


    }

    @Override
    public void onBuySuccess(BuyResponse body) {
        Intent intent = new Intent(mContext, TransationsRecordActivity.class);
        intent.putExtra("tradeid", body.getTradeid()+"");
        startActivity(intent);
    }

    @OnClick({R.id.rl_bank_card, R.id.tv_ld_protocol, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bank_card:
//                showCardPopupWindow();
                break;
            case R.id.tv_ld_protocol:
                break;
            case R.id.btn_submit:
                if (!cbAgreement.isChecked()) {
                    showToast("请阅读并同意服务协议");
                    return;
                }
                showPswPopupWindow();
                break;
        }
    }

    private void showPswPopupWindow() {
        setupPswPopupWindow();
        mPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //    private void showCardPopupWindow() {
//        mCardPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pay_back:
                dismissPswPopWindow();
                break;
            case R.id.tv_pay_forgetPwd:
                Toast.makeText(mContext, "忘记密码", Toast.LENGTH_SHORT).show();
                mPayView.clearPassword();
                break;
            case R.id.tv_pop_card_back:
                mCardPopupWindow.dismiss();
                break;
        }
    }
}
