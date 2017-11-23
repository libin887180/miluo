package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.TradeStepAdapter;
import com.zhongdi.miluo.adapter.mine.TransInfoAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.TradeRecord;
import com.zhongdi.miluo.model.TradeRecord.Part2Bean.StepsBean;
import com.zhongdi.miluo.presenter.TransactionRecordPresenter;
import com.zhongdi.miluo.ui.activity.MainActivity;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.ui.activity.login.TestActivity;
import com.zhongdi.miluo.ui.activity.market.FundCurrencyDetailActivity;
import com.zhongdi.miluo.ui.activity.market.FundDetailActivity;
import com.zhongdi.miluo.ui.activity.market.SellFundActivity;
import com.zhongdi.miluo.util.StringUtil;
import com.zhongdi.miluo.view.TransactionRecordView;
import com.zhongdi.miluo.widget.AlertDialog;
import com.zhongdi.miluo.widget.NOScollListView;
import com.zhongdi.miluo.widget.OnPasswordInputFinish;
import com.zhongdi.miluo.widget.PayView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TransationsRecordActivity extends BaseActivity<TransactionRecordPresenter> implements TransactionRecordView, View.OnClickListener {

    @BindView(R.id.listview)
    NOScollListView listview;
    TransInfoAdapter transAdapter;
    @BindView(R.id.tv_trade_name)
    TextView tvTradeName;
    @BindView(R.id.tv_reqtime)
    TextView tvReqtime;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.iv_steps)
    ImageView ivSteps;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_steps)
    LinearLayout llSteps;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;

    private PopupWindow mPopupWindow;
    private View popView;
    private PayView mPayView;
    TradeRecord tradeRecord;
    TradeStepAdapter stepAdapter;
    private String tradeid;
    private String tradeType;
    List<StepsBean> tradeSteps = new ArrayList<>();
    List<TradeRecord.Part3Bean> transInfo = new ArrayList<>();
    private String SOURCE;
    private int fundType;
    private boolean showRisk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tradeid = getIntent().getStringExtra("tradeid");
        tradeType = getIntent().getStringExtra("tradType");
        SOURCE = getIntent().getStringExtra(IntentConfig.SOURCE);
        showRisk = getIntent().getBooleanExtra("showriskDialog",false);
        binding(R.layout.activity_transaction_record);
    }

    @Override
    protected TransactionRecordPresenter initPresenter() {
        return new TransactionRecordPresenter(this);
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

    @Override
    public void reLogin() {
        Intent intent  = new Intent(mContext, QuickLoginActivity.class);
        startActivityForResult(intent, 301);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.getTransRecord(tradeid, tradeType);
    }

    @Override
    protected void initialize() {
        if (TextUtils.isEmpty(SOURCE)) {
            btn_back.setVisibility(View.VISIBLE);
            tvTitleRight.setVisibility(View.GONE);
        } else {
            if (SOURCE.equals("sell") || SOURCE.equals("buy")) {
                btn_back.setVisibility(View.GONE);
                tvTitleRight.setVisibility(View.VISIBLE);
            } else {
                btn_back.setVisibility(View.VISIBLE);
                tvTitleRight.setVisibility(View.GONE);
            }
        }

        presenter.getTransRecord(tradeid, tradeType);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        transAdapter = new TransInfoAdapter(mContext);
        transAdapter.setDataList(transInfo);
        listview.setFocusable(false);
        listview.setAdapter(transAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent detail;
                    if ((fundType + "").equals(MiluoConfig.DUANQI) || (fundType + "").equals(MiluoConfig.HUOBI)) {
                        detail = new Intent(mContext, FundCurrencyDetailActivity.class);
                    } else {
                        detail = new Intent(mContext, FundDetailActivity.class);

                    }
                    detail.putExtra("fundId", tradeRecord.getPart1().getFundid() + "");
                    detail.putExtra("fundcode", tradeRecord.getPart1().getFundcode() + "");
                    startActivity(detail);
                }
            }
        });
//        showToast(tradeid);
    }

    @Override
    public void OnDataSuccess(TradeRecord body) {
        this.tradeRecord = body;
        tradeSteps.clear();
        fundType = body.getPart1().getFundtype();
        String cancelstatus = body.getPart1().getCancelstatus();
        String currentStep = body.getPart2().getCurrentStep();
        String title = body.getPart1().getTitle();
        tvTradeName.setText(title);
        tvReqtime.setText(body.getPart1().getReqtime());

        if (tradeType.equals("1")) {
            tvAmount.setText("-" + StringUtil.parseStr2Num(body.getPart1().getAmount() + ""));
        } else {
            tvAmount.setText(StringUtil.parseStr2Num(body.getPart1().getAmount() + ""));
        }
        if (body.getPart2().getSteps() != null && body.getPart2().getSteps().size() > 0) {
            tradeSteps.addAll(body.getPart2().getSteps());
            llEmpty.setVisibility(View.GONE);
            llSteps.setVisibility(View.VISIBLE);
            if (body.getPart2().getSteps().size() == 3) {
                switch (currentStep) {
                    case "1":
                        ivSteps.setImageResource(R.drawable.step3_1);
                        break;
                    case "2":
                        ivSteps.setImageResource(R.drawable.step3_2);
                        break;
                    case "3":
                        ivSteps.setImageResource(R.drawable.step3_3);
                        break;
                }
            } else {
                ivSteps.setImageResource(R.drawable.step2_2);
            }

        } else {
            llEmpty.setVisibility(View.VISIBLE);
            tvResult.setText(body.getPart2().getResult());
            llSteps.setVisibility(View.GONE);
        }
        stepAdapter = new TradeStepAdapter(mContext, tradeSteps, cancelstatus, Integer.parseInt(currentStep), title);
        recyclerView.setAdapter(stepAdapter);

        stepAdapter.setOnItemChildClickListener(R.id.tv_cancel_status, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((TextView) v).getText().toString().equals("赎回")) {
                    Intent intent = new Intent(mContext, SellFundActivity.class);
                    intent.putExtra("fundCode", tradeRecord.getPart1().getFundcode());
                    intent.putExtra(IntentConfig.SOURCE, IntentConfig.Trade_Record_Redeem);
                    startActivity(intent);
                } else {
                    showPswPopupWindow();
                }
            }
        });

        transInfo.clear();
        if (body.getPart3() != null && body.getPart3().size() > 0) {
            transInfo.addAll(body.getPart3());
        }
        transAdapter.notifyDataSetChanged();
        if (SpCacheUtil.getInstance().getUserTestLevel() == -1) {//没有测评
            showTestDialog();
            return;
        }
    }
    public void showTestDialog() {
        new AlertDialog(mContext).builder().setTitle("风险测评邀请").setMsg("国家证监会发《130号文》邀请您参与基金投资者风险评测，完成后可以参与更多优惠活动哦！")
                .setNegativeButton("残忍拒绝", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(mContext, MainActivity.class);
//                        intent.putExtra("to", "mine");
//                        startActivity(intent);
//                        finish();

                    }
                }).setPositiveButton("立即测评", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TestActivity.class);
                intent.putExtra(IntentConfig.SOURCE, IntentConfig.TIYANJIN);
                intent.putExtra(IntentConfig.MAIDIAN, IntentConfig.EX_Gold_Complete_Risk);
                startActivity(intent);
                finish();
            }
        }).show();
    }
    private void showPswPopupWindow() {
        setupPswPopupWindow();
        mPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
                presenter.fundWithdraw(tradeid, mPayView.getPassword(), tradeType);
                dismissPswPopWindow();
            }
        });
        mPayView.getCancel().setOnClickListener(this);
        mPayView.getForgetPsw().setOnClickListener(this);
    }

    @Override
    public void dismissPswPopWindow() {
        mPopupWindow.dismiss();
    }

    @Override
    public void OnChedanSuccess() {
        showToast("撤单成功");
        presenter.getTransRecord(tradeid, tradeType);
        setResult(1001);
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
                break;
        }
    }

    @OnClick(R.id.tv_title_right)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("to", "mine");
        startActivity(intent);
        finish();
    }
}
