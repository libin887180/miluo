package com.zhongdi.miluo.ui.activity.market;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.FundDetail;
import com.zhongdi.miluo.model.FundManagerInfo;
import com.zhongdi.miluo.model.FundNotice;
import com.zhongdi.miluo.presenter.FundDetailPresenter;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.util.TimeUtil;
import com.zhongdi.miluo.util.xUtilsImageUtils;
import com.zhongdi.miluo.view.FundDetailView;

import butterknife.BindView;
import butterknife.OnClick;

public class FundCurrencyDetailActivity extends BaseActivity<FundDetailPresenter> implements FundDetailView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_fund_type)
    TextView tvFundType;
    @BindView(R.id.tv_fund_levle)
    TextView tvFundLevle;
    @BindView(R.id.tv_title_right)
    ImageView tvTitleRight;
    @BindView(R.id.tv_fundSize)
    TextView tvFundSize;
    @BindView(R.id.tv_estabdate)
    TextView tvEstabdate;
    @BindView(R.id.tv_increase)
    TextView tvIncrease;
    @BindView(R.id.tv_profit)
    TextView tvProfit;
    @BindView(R.id.tv_fund_company_name)
    TextView tvFundCompanyName;
    @BindView(R.id.iv_manager_icon)
    ImageView ivManagerIcon;
    @BindView(R.id.tv_manager_name)
    TextView tvManagerName;
    @BindView(R.id.tv_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_fund_notice)
    TextView tvFundNotice;
    @BindView(R.id.tv_notice_date)
    TextView tvNoticeDate;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.tv_percent)
    TextView tvPercent;
    @BindView(R.id.ll_his_value)
    LinearLayout llHisValue;
    private View sharePopView;
    private PopupWindow mCardPopupWindow;
    private String sellFundId;
    private String fundCode;
    private FundManagerInfo managerInfo;
    FundDetail fundDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sellFundId = getIntent().getStringExtra("fundId");
        binding(R.layout.activity_fund_currency_detail);
    }

    @Override
    protected FundDetailPresenter initPresenter() {
        return new FundDetailPresenter(this);
    }

    @Override
    protected void initialize() {
        setupSharePopupWindow();
        tvTitleRight.setTag(0);
        presenter.getFundDetail(sellFundId);
        presenter.getFundManagerInfo(sellFundId);
        presenter.getFundNotice(sellFundId);
        setListener();

    }

    @Override
    public void setListener() {


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
    public void reLogin() {
        Intent intent  = new Intent(mContext, QuickLoginActivity.class);
        startActivityForResult(intent, 301);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void OnFundManagerSuccess(FundManagerInfo managerInfo) {
        this.managerInfo = managerInfo;
        tvManagerName.setText(managerInfo.getManagerName());
        tvStartDate.setText("从业时间:" + managerInfo.getStartDate() + "至今");
//        Glide.with(mContext).load(managerInfo.getIndiImgUrl()).apply(new RequestOptions().placeholder(R.drawable.head_default).error(R.drawable.head_default))
//                .into(ivManagerIcon);
        xUtilsImageUtils.display(ivManagerIcon, managerInfo.getIndiImgUrl(), true, R.drawable.head_default, R.drawable.head_default);
    }

    @Override
    public void OnFundNoticeSuccess(FundNotice notice) {
        tvFundNotice.setText(notice.getTitle());
        tvNoticeDate.setText(notice.getPubDate());
    }

    @Override
    public void OnCollectSuccess() {
        tvTitleRight.setBackgroundResource(R.drawable.ic_collected);
        tvTitleRight.setTag(1);
    }

    @Override
    public void OnDisCollectSuccess() {
        tvTitleRight.setBackgroundResource(R.drawable.ic_no_collect);
        tvTitleRight.setTag(0);
    }

    private void showpSharePopupWindow() {
        mCardPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    // 显示弹窗
    public void setupSharePopupWindow() {
        // 初始化弹窗
        sharePopView = View.inflate(this, R.layout.pop_share_view, null);
        mCardPopupWindow = new PopupWindow(sharePopView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        sharePopView.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardPopupWindow.dismiss();
            }
        });
        // 设置动画
        mCardPopupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        // mPopupWindow.showAsDropDown(findViewById(R.id.head), 0, 0);
        mCardPopupWindow.setOutsideTouchable(true);
    }

    @OnClick({R.id.rl_fund_manager, R.id.rl_fund_notice, R.id.rl_premium, R.id.rl_archives, R.id.tv_buy, R.id.img_title_right, R.id.tv_title_right,R.id.ll_his_value})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_fund_manager:
                if (managerInfo == null) {
                    showToast("暂未获取到基金经理信息");
                    return;
                }
                Intent managerIntent = new Intent(mContext, ManagerDetailActivity.class);
                managerIntent.putExtra("managerDetail", managerInfo);
                startActivity(managerIntent);
                break;
            case R.id.rl_fund_notice:
                Intent noticeIntent = new Intent(mContext, FundNoticeActivity.class);
                noticeIntent.putExtra("fundId", sellFundId);
                startActivity(noticeIntent);
                break;
            case R.id.rl_premium:
                Intent rateIntent = new Intent(mContext, PremiumActivity.class);
                rateIntent.putExtra("fundId", sellFundId);
                startActivity(rateIntent);
                break;
            case R.id.rl_archives:
                Intent archivesIntent = new Intent(mContext, FundAchivesActivity.class);
                archivesIntent.putExtra("fundId", sellFundId);
                startActivity(archivesIntent);
                break;
            case R.id.tv_buy:
                if (!MyApplication.getInstance().isLogined) {
                    Intent intent = new Intent(mContext, QuickLoginActivity.class);
                    startActivityForResult(intent, 101);
                    return;
                }
                if (!TextUtils.isEmpty(fundCode)) {
                    Intent buyIntent = new Intent(mContext, BuyFundActivity.class);
                    buyIntent.putExtra("fundCode", fundCode);
                    startActivity(buyIntent);
                } else {
                    showToast("暂未获取到基金代码");
                }

                break;
            case R.id.img_title_right:
                showpSharePopupWindow();
                break;
            case R.id.ll_his_value:
                Intent hisIntent = new Intent(mContext, FundHistoryValueActivity.class);
                hisIntent.putExtra("fundId", sellFundId);
                hisIntent.putExtra(IntentConfig.SOURCE, "currency");
                startActivity(hisIntent);
                break;
            case R.id.tv_title_right:
                if (!MyApplication.getInstance().isLogined) {
                    Intent intent = new Intent(mContext, QuickLoginActivity.class);
                    startActivityForResult(intent, 101);
                    return;
                }
                int tag = (int) tvTitleRight.getTag();
                if (tag == 0) {
                    presenter.collectFund(sellFundId);
                } else {
                    presenter.discollectFund(sellFundId);
                }

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mCardPopupWindow != null && mCardPopupWindow.isShowing()) {
                mCardPopupWindow.dismiss();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void OnDataSuccess(FundDetail fundDetail) {
//        this.fundDetail = fundDetail;
        fundCode = fundDetail.getFundCode();

        if (fundDetail.getYearyld().contains("-")) {
            tvIncrease.setTextColor(mContext.getResources().getColor(R.color.increase_green));
            tvPercent.setTextColor(mContext.getResources().getColor(R.color.increase_green));

        } else {
            tvIncrease.setTextColor(mContext.getResources().getColor(R.color.red));
            tvPercent.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        tvIncrease.setText(fundDetail.getYearyld());
        tvProfit.setText(fundDetail.getTenthouunitincm());
        title.setText(fundDetail.getFundName() + "\n" + fundDetail.getFundCode() );
        if (fundDetail.getStatus().equals("1")) {
            tvTitleRight.setBackgroundResource(R.drawable.ic_collected);
            tvTitleRight.setTag(1);
        } else {
            tvTitleRight.setBackgroundResource(R.drawable.ic_no_collect);
            tvTitleRight.setTag(0);
        }
        tvFundCompanyName.setText(fundDetail.getFundManagerName());
        tvFundSize.setText(fundDetail.getFundSize() + "亿元");
        tvEstabdate.setText(TimeUtil.changeToYYMMDD(fundDetail.getEstabDate()));
        switchFundType(fundDetail.getFundType());
        switchRiskLevel(Integer.parseInt(fundDetail.getRiskLevel()));

        if (fundDetail.getBuyStatus().equals("0")) {//（0-不能购买，1-可以购买）
            tvBuy.setText("暂不开放 敬请期待");
            tvBuy.setEnabled(false);
        } else {
            tvBuy.setText("立即购买");
            tvBuy.setEnabled(true);
        }
    }

    private void switchRiskLevel(int risklevel) {

        switch (risklevel) {
            case MiluoConfig.RISK_LOW:
                tvFundLevle.setText("低风险");
                tvFundLevle.setBackgroundResource(R.drawable.ic_signblue);
                break;
            case MiluoConfig.RISK_MID_LOW:
                tvFundLevle.setText("中低风险");
                tvFundLevle.setBackgroundResource(R.drawable.ic_signblue);
                break;
            case MiluoConfig.RISK_MID:
                tvFundLevle.setText("中风险");
                tvFundLevle.setBackgroundResource(R.drawable.ic_signyellow);
                break;
            case MiluoConfig.RISK_MID_HIGH:
                tvFundLevle.setText("中高风险");
                tvFundLevle.setBackgroundResource(R.drawable.ic_signred);
                break;
            case MiluoConfig.RISK_HIGH:
                tvFundLevle.setText("高风险");
                tvFundLevle.setBackgroundResource(R.drawable.ic_signred);
                break;
        }
    }

    private void switchFundType(String fundType) {

        switch (fundType) {
            case MiluoConfig.GUPIAO:
                tvFundType.setText("股票型");
                tvFundType.setBackgroundResource(R.drawable.ic_signred);
                break;
            case MiluoConfig.ZHAIQUAN:
                tvFundType.setText("债券型");
                tvFundType.setBackgroundResource(R.drawable.ic_signred);
                break;
            case MiluoConfig.HUNHE:
                tvFundType.setText("混合型");
                tvFundType.setBackgroundResource(R.drawable.ic_signyellow);
                break;
            case MiluoConfig.HUOBI:
                tvFundType.setText("货币型");
                tvFundType.setBackgroundResource(R.drawable.ic_signblue);
                break;
            case MiluoConfig.ZHISHU:
                tvFundType.setText("指数型");
                tvFundType.setBackgroundResource(R.drawable.ic_signyellow);
                break;
            case MiluoConfig.BAOBEN:
                tvFundType.setText("保本型");
                tvFundType.setBackgroundResource(R.drawable.ic_signyellow);
                break;
            case MiluoConfig.ETF:
                tvFundType.setText("ETF联接");
                tvFundType.setBackgroundResource(R.drawable.ic_signyellow);
                break;
            case MiluoConfig.DQII:
                tvFundType.setText("QDII");
                tvFundType.setBackgroundResource(R.drawable.ic_signred);
                break;
            case MiluoConfig.LOF:
                tvFundType.setText("LOF");
                tvFundType.setBackgroundResource(R.drawable.ic_signyellow);
                break;
            case MiluoConfig.DUANQI:
                tvFundType.setText("短期理财型");
                tvFundType.setBackgroundResource(R.drawable.ic_signblue);
                break;
            case MiluoConfig.ALL:
                tvFundType.setText("全部");
                break;
            case MiluoConfig.ZUHE:
                tvFundType.setText("组合型");
                tvFundType.setBackgroundResource(R.drawable.ic_signyellow);
                break;
        }
    }
}
