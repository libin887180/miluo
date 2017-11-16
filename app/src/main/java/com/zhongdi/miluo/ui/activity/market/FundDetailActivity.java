package com.zhongdi.miluo.ui.activity.market;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.FundDetail;
import com.zhongdi.miluo.model.FundManagerInfo;
import com.zhongdi.miluo.model.FundNotice;
import com.zhongdi.miluo.presenter.FundDetailPresenter;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.ui.fragment.fund.HalfYearLineFragment;
import com.zhongdi.miluo.ui.fragment.fund.MonthLineFragment;
import com.zhongdi.miluo.ui.fragment.fund.SeasonLineFragment;
import com.zhongdi.miluo.ui.fragment.fund.YearLineFragment;
import com.zhongdi.miluo.util.TimeUtil;
import com.zhongdi.miluo.util.xUtilsImageUtils;
import com.zhongdi.miluo.view.FundDetailView;
import com.zhongdi.miluo.widget.NoScrollViewPager;
import com.zhongdi.miluo.widget.SegmentControl;

import butterknife.BindView;
import butterknife.OnClick;

public class FundDetailActivity extends BaseActivity<FundDetailPresenter> implements FundDetailView {

    @BindView(R.id.segment_control)
    SegmentControl segmentControl;
    @BindView(R.id.mViewPager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_year_rate)
    TextView tvYearRate;
    @BindView(R.id.tv_fund_type)
    TextView tvFundType;
    @BindView(R.id.tv_fund_levle)
    TextView tvFundLevle;
    @BindView(R.id.tv_day_rate)
    TextView tvDayRate;
    @BindView(R.id.tv_net_value_date)
    TextView tvNetValueDate;
    @BindView(R.id.tv_net_value)
    TextView tvNetValue;
    @BindView(R.id.tv_title_right)
    ImageView tvTitleRight;
    @BindView(R.id.tv_fundSize)
    TextView tvFundSize;
    @BindView(R.id.tv_estabdate)
    TextView tvEstabdate;
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
    @BindView(R.id.tv_current_rate)
    TextView tvCurrentRate;
    @BindView(R.id.tv_dep_rate)
    TextView tvDepRate;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.tv_percent)
    TextView tvPercent;
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
        binding(R.layout.activity_fund_detail);
    }

    @Override
    protected FundDetailPresenter initPresenter() {
        return new FundDetailPresenter(this);
    }

    @Override
    protected void initialize() {
        setupSharePopupWindow();
        mViewPager.setScroll(false);
        segmentControl.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
        tvTitleRight.setTag(0);
        presenter.getFundDetail(sellFundId);
        presenter.getFundManagerInfo(sellFundId);
        presenter.getFundNotice(sellFundId);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

//        adapter.addFragment(EstimateFragment.newInstance( getIntent().getStringExtra("fundcode")));//估值图
        adapter.addFragment(MonthLineFragment.newInstance(sellFundId));
        adapter.addFragment(SeasonLineFragment.newInstance(sellFundId));
        adapter.addFragment(HalfYearLineFragment.newInstance(sellFundId));
        adapter.addFragment(YearLineFragment.newInstance(sellFundId));
        mViewPager.setAdapter(adapter);
        setListener();

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
    public void setListener() {

        /**
         * 设置viewpager的选择事件
         */
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                segmentControl.setSelectedIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

    @OnClick({R.id.rl_fund_manager, R.id.rl_fund_notice, R.id.rl_premium, R.id.rl_archives, R.id.rl_fund_history, R.id.tv_buy, R.id.img_title_right, R.id.tv_title_right})
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
            case R.id.rl_fund_history:
                Intent hisIntent = new Intent(mContext, FundHistoryValueActivity.class);
                hisIntent.putExtra("fundId", sellFundId);
                startActivity(hisIntent);
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
        title.setText(fundDetail.getFundName() + "\n" + fundDetail.getFundCode() + "");

        if (fundDetail.getYearRate().contains("-")) {
            tvYearRate.setTextColor(mContext.getResources().getColor(R.color.increase_green));
            tvPercent.setTextColor(mContext.getResources().getColor(R.color.increase_green));

        } else {
            tvYearRate.setTextColor(mContext.getResources().getColor(R.color.red));
            tvPercent.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        tvYearRate.setText(fundDetail.getYearRate());
        tvNetValue.setText(fundDetail.getNetValue());
        if (fundDetail.getYearRate().contains("-")) {
            tvDayRate.setTextColor(mContext.getResources().getColor(R.color.increase_green));
        } else {
            tvDayRate.setTextColor(mContext.getResources().getColor(R.color.red));
        }

        tvDayRate.setText(fundDetail.getDayRate()+"%");
        tvNetValueDate.setText("单位净值(" + fundDetail.getValueDate() + ")");
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
        tvDepRate.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        if (!TextUtils.isEmpty(fundDetail.getDiscount())&&Double.parseDouble(fundDetail.getDiscount())>0&&Double.parseDouble(fundDetail.getDiscount())<1) {
            double discount = Double.parseDouble(fundDetail.getDiscount());
            if(fundDetail.getRateValue().length()>0) {
                String rate = fundDetail.getRateValue().substring(0, fundDetail.getRateValue().length() - 1);
                float value = Float.parseFloat(rate);
                tvCurrentRate.setText(value*discount+"%");
            }else{
                tvCurrentRate.setText("0%");
            }
            tvDepRate.setText(fundDetail.getRateValue());
        } else {
            tvDepRate.setVisibility(View.GONE);
            tvCurrentRate.setText(fundDetail.getRateValue());
        }

        if (fundDetail.getBuyStatus().equals("0")) {//（0-不能购买，1-可以购买）
            tvBuy.setEnabled(false);
        } else {
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
