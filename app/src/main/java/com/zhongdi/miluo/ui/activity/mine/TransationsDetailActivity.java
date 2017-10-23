package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.FenhongTypeAdapter;
import com.zhongdi.miluo.adapter.mine.TransAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.DealRecord;
import com.zhongdi.miluo.model.PropertyDetail;
import com.zhongdi.miluo.presenter.TransactionDetailPresenter;
import com.zhongdi.miluo.ui.activity.market.BuyFundActivity;
import com.zhongdi.miluo.ui.activity.market.FundDetailActivity;
import com.zhongdi.miluo.ui.activity.market.SellFundActivity;
import com.zhongdi.miluo.view.TransactionDetailView;
import com.zhongdi.miluo.widget.OnPasswordInputFinish;
import com.zhongdi.miluo.widget.PayView;
import com.zhongdi.miluo.widget.RecycleViewDivider;
import com.zhongdi.miluo.widget.mpchart.MyLineChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TransationsDetailActivity extends BaseActivity<TransactionDetailPresenter> implements TransactionDetailView, View.OnClickListener {

    @BindView(R.id.line_chart)
    MyLineChart mChart;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.iv_fenhong)
    ImageView ivFenhong;
    @BindView(R.id.tv_fenhong)
    TextView tvFenhong;
    @BindView(R.id.tv_fund_name)
    TextView tvFundName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_risk)
    TextView tvRisk;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.tv_profit)
    TextView tvProfit;
    @BindView(R.id.tv_total_share)
    TextView tvTotalShare;
    @BindView(R.id.tv_avaliable_share)
    TextView tvAvaliableShare;
    @BindView(R.id.tv_accumulated_income)
    TextView tvAccumulatedIncome;
    @BindView(R.id.tv_dayrate)
    TextView tvDayrate;
    @BindView(R.id.tv_netvalue)
    TextView tvNetvalue;
    @BindView(R.id.tv_totalshare_income)
    TextView tvTotalshareIncome;
    @BindView(R.id.tv_increase_type)
    TextView tvIncreaseType;
    @BindView(R.id.tv_netvalue_type)
    TextView tvNetvalueType;
    @BindView(R.id.tv_sell)
    TextView tvSell;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    private TransAdapter adapter;
    private RecyclerView fenhongRv;
    private FenhongTypeAdapter listAdapter;
    private PopupWindow mCardPopupWindow;
    private View fenhongPopView;
    private String fundcode = "";
    private int pageIndex = 1;
    List<DealRecord> dealRecords = new ArrayList<>();
    private String fundId;

    private PopupWindow mPopupWindow;
    private View popView;
    private PayView mPayView;
    private String selectFenHong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fundcode = getIntent().getStringExtra("fundcode");
        binding(R.layout.activity_transaction_detail);
    }

    @Override
    protected TransactionDetailPresenter initPresenter() {
        return new TransactionDetailPresenter(this);
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
                if (TextUtils.isEmpty(selectFenHong)) {
                    showToast("请选择分红方式");
                }
                presenter.modifyBonus(selectFenHong, fundcode, mPayView.getPassword());
//                presenter.buyFund(fundCode, mPayView.getPassword(), etMoney.getText().toString());
                dismissPswPopWindow();
            }
        });
        mPayView.getCancel().setOnClickListener(this);
        mPayView.getForgetPsw().setOnClickListener(this);
    }

    private void showPswPopupWindow() {
        setupPswPopupWindow();
        mPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void showFenHongPopupWindow() {
        mCardPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void dismissPswPopWindow() {
        mPopupWindow.dismiss();
    }

    @Override
    public void modifyBonusSuccess() {
        presenter.getPropertyDetail(fundcode);
    }

    @Override
    public void modifyBonusFailed() {
        presenter.getPropertyDetail(fundcode);
    }


    @Override
    protected void initialize() {
        setupCardPopupWindow();
        refreshLayout.setEnableRefresh(false);//不可下拉刷新
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setEnableOverScroll(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//                pageIndex=1;
//                presenter.getTradRecords(pageIndex, MiluoConfig.DEFAULT_PAGESIZE, fundcode);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refresh) {

                presenter.getTradRecords(pageIndex, MiluoConfig.DEFAULT_PAGESIZE, fundcode);
            }
        });
        setUpChart();
        adapter = new TransAdapter(mContext, dealRecords);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {

            }
        });

        presenter.getPropertyDetail(fundcode);
        presenter.getLines(fundcode);
        presenter.getTradRecords(pageIndex, MiluoConfig.DEFAULT_PAGESIZE, fundcode);
    }

    @Override
    public void dismissLoadingDialog() {
        getLoadingProgressDialog().dismiss();
    }

    @Override
    public void showLoadingDialog() {
        getLoadingProgressDialog().show();
    }

    public void setChartData() {
        ArrayList<Entry> values = new ArrayList<Entry>();
        ArrayList<Entry> values2 = new ArrayList<Entry>();
        ArrayList<Entry> values3 = new ArrayList<Entry>();
        for (int i = 0; i < 40; i++) {

            float val = (float) (Math.random() * 100) + 3;
            values.add(new Entry(i, val));
            if (i == 30) {
                values2.add(new Entry(i, val));
            }
            if (i == 20) {
                values3.add(new Entry(i, val));
            }
        }
        LineDataSet set1;
        LineDataSet set2;
        LineDataSet set3;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set2.setValues(values2);
            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set3.setValues(values3);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "DataSet 1");
            set1.setColor(Color.parseColor("#FF2C40"));
            set1.setLineWidth(2f);
            set1.setHighlightEnabled(true);
            set1.setHighLightColor(Color.parseColor("#FBE1B9"));
            set1.setDrawCircleHole(false);
            set1.setDrawIcons(false);
            set1.setDrawCircles(false);
            set1.setDrawValues(false);
            set1.setDrawFilled(true);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);
            set2 = new LineDataSet(values2, "DataSet 2");
            set2.setColor(Color.parseColor("#6BAEE9"));
            set2.setLineWidth(2f);
            set2.setHighlightEnabled(true);
            set2.setDrawCircleHole(false);
            set2.setDrawIcons(false);
            set2.setDrawCircles(true);
            set2.setCircleColor(Color.parseColor("#6BAEE9"));
            set2.setDrawValues(false);
            set2.setDrawFilled(false);


            set3 = new LineDataSet(values3, "DataSet 3");
            set3.setColor(Color.parseColor("#6BAEE9"));
            set3.setLineWidth(2f);
            set3.setHighlightEnabled(true);
            set3.setDrawCircleHole(false);
            set3.setDrawIcons(false);
            set3.setDrawCircles(true);
            set3.setCircleColor(Color.parseColor("#FF2C40"));
            set3.setDrawValues(false);
            set3.setDrawFilled(false);
            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.parseColor("#70FFE9EB"));
            }
            LineData data = new LineData(set1, set2, set3);

            // set data
            mChart.setData(data);
        }
    }

    @OnClick({R.id.tv_sell, R.id.tv_buy, R.id.ll_fenhong, R.id.rl_fund_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_buy:
                Intent buyIntent = new Intent(mContext, BuyFundActivity.class);
                buyIntent.putExtra("fundCode", fundcode);
                startActivity(buyIntent);
                break;
            case R.id.tv_sell:
                Intent intent = new Intent(mContext, SellFundActivity.class);
                intent.putExtra("fundCode", fundcode);
                startActivity(intent);
                break;
            case R.id.ll_fenhong:
                showFenHongPopupWindow();
                break;
            case R.id.rl_fund_info:
                if (!TextUtils.isEmpty(fundId)) {
                    Intent detail = new Intent(mContext, FundDetailActivity.class);
                    detail.putExtra("fundId", fundId);
                    detail.putExtra("fundcode", fundcode);
                    startActivity(detail);
                }
                break;
        }
    }


    // 显示弹窗
    public void setupCardPopupWindow() {
        // 初始化弹窗
        fenhongPopView = View.inflate(this, R.layout.pop_fenhong_view, null);
        mCardPopupWindow = new PopupWindow(fenhongPopView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        fenhongPopView.findViewById(R.id.gray_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardPopupWindow.dismiss();
            }
        });
        fenhongPopView.findViewById(R.id.tv_pop_card_back).setOnClickListener(this);
        fenhongRv = (RecyclerView) fenhongPopView.findViewById(R.id.rl_card_list);
        List<String> datas = new ArrayList<>();
        datas.add("红利再投");
        datas.add("现金分红");
        //分红方式  0红利再投  1现金分红
        listAdapter = new FenhongTypeAdapter(mContext, datas);
        fenhongRv.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        fenhongRv.setLayoutManager(new LinearLayoutManager(mContext));
        fenhongRv.setAdapter(listAdapter);
        listAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                if (listAdapter.getSelectPosotion() == position) {
                    mCardPopupWindow.dismiss();
                    return;
                }
                selectFenHong = position + "";
                mCardPopupWindow.dismiss();
                showPswPopupWindow();


            }
        });
        // 设置动画
        mCardPopupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        // mPopupWindow.showAsDropDown(findViewById(R.id.head), 0, 0);
        mCardPopupWindow.setOutsideTouchable(true);
    }

    @Override
    public void setUpChart() {
        mChart.getDescription().setEnabled(false);//描述不可见
//        mChart.setTouchEnabled(false);//不可触摸
        mChart.setExtraOffsets(5, 10, 5, 20);
        mChart.setDragEnabled(true);//不可拖拽，高亮线
        mChart.setScaleEnabled(false);//不可伸缩
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(45);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setXOffset(10f);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setGranularity(50);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawAxisLine(true);//Y轴坐标

        mChart.getAxisRight().setEnabled(false);
        setChartData();
        Legend legend = mChart.getLegend();

        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setYOffset(20f);
        mChart.animateX(1000);
    }

    @Override
    public void onTradRecordsSuccess(List<DealRecord> records) {
        if (pageIndex == 1) {
            dealRecords.clear();
        }
        if (records.size() < MiluoConfig.DEFAULT_PAGESIZE) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
            pageIndex++;
        }
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();

        dealRecords.addAll(records);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPropertySuccess(PropertyDetail body) {
        fundId = body.getFundid() + "";
        tvFundName.setText(body.getFundname() + "(" + body.getFundcode() + ")");
        switchRiskLevel(body.getRisklevel());
        switchFundType(body.getFundtype() + "");

        tvValue.setText(body.getMarketval() + "");
        tvProfit.setText(body.getDayincome() + "");

        tvAccumulatedIncome.setText(body.getAccumulatedincome() + "");
        tvAvaliableShare.setText(body.getAvaliableshare());

        tvTotalShare.setText(body.getTotalshare() + "");
        tvTotalshareIncome.setText(body.getTotalshareincome() + "");

        if (body.getRedeemstatus() == 1) {
            tvSell.setEnabled(true);
        } else {
            tvSell.setEnabled(false);
        }
        if (body.getBuystatus() == 1) {
            tvBuy.setEnabled(true);
        } else {
            tvSell.setEnabled(false);
        }
        if ((body.getFundtype() + "").equals(MiluoConfig.HUOBI)) {
            tvIncreaseType.setText("七日年化");
            tvNetvalueType.setText("万分收益");
            tvNetvalue.setText(body.getTenthouunitincm() + "");
            tvDayrate.setText(body.getYearyld() + "");
        } else {
            tvIncreaseType.setText("日涨幅");
            tvNetvalueType.setText("最新净值");
            tvNetvalue.setText(body.getNetvalue() + "");
            tvDayrate.setText(body.getDayrate() + "");
        }
        if (body.getDividendMethod().equals("0")) {
            listAdapter.setCheck(0);
            tvFenhong.setText("红利再投");
        } else {
            listAdapter.setCheck(1);
            tvFenhong.setText("现金分红");
        }


    }

    private void switchFundType(String fundType) {

        switch (fundType) {
            case MiluoConfig.GUPIAO:
                tvType.setText("股票型");
                tvType.setBackgroundResource(R.drawable.ic_signred);
                break;
            case MiluoConfig.ZHAIQUAN:
                tvType.setText("债券型");
                tvType.setBackgroundResource(R.drawable.ic_signred);
                break;
            case MiluoConfig.HUNHE:
                tvType.setText("混合型");
                tvType.setBackgroundResource(R.drawable.ic_signyellow);
                break;
            case MiluoConfig.HUOBI:
                tvType.setText("货币型");
                tvType.setBackgroundResource(R.drawable.ic_signblue);
                break;
            case MiluoConfig.ZHISHU:
                tvType.setText("指数型");
                tvType.setBackgroundResource(R.drawable.ic_signyellow);
                break;
            case MiluoConfig.BAOBEN:
                tvType.setText("保本型");
                tvType.setBackgroundResource(R.drawable.ic_signyellow);
                break;
            case MiluoConfig.ETF:
                tvType.setText("ETF联接");
                tvType.setBackgroundResource(R.drawable.ic_signyellow);
                break;
            case MiluoConfig.DQII:
                tvType.setText("QDII");
                tvType.setBackgroundResource(R.drawable.ic_signred);
                break;
            case MiluoConfig.LOF:
                tvType.setText("LOF");
                tvType.setBackgroundResource(R.drawable.ic_signyellow);
                break;
            case MiluoConfig.DUANQI:
                tvType.setText("短期理财型");
                tvType.setBackgroundResource(R.drawable.ic_signblue);
                break;
            case MiluoConfig.ALL:
                tvType.setText("全部");
                break;
            case MiluoConfig.ZUHE:
                tvType.setText("组合型");
                tvType.setBackgroundResource(R.drawable.ic_signyellow);
                break;
        }
    }

    private void switchRiskLevel(int risklevel) {

        switch (risklevel) {
            case MiluoConfig.RISK_LOW:
                tvRisk.setText("低风险");
                tvRisk.setBackgroundResource(R.drawable.ic_signblue);
                break;
            case MiluoConfig.RISK_MID_LOW:
                tvRisk.setText("中低风险");
                tvRisk.setBackgroundResource(R.drawable.ic_signblue);
                break;
            case MiluoConfig.RISK_MID:
                tvRisk.setText("中风险");
                tvRisk.setBackgroundResource(R.drawable.ic_signyellow);
                break;
            case MiluoConfig.RISK_MID_HIGH:
                tvRisk.setText("中高风险");
                tvRisk.setBackgroundResource(R.drawable.ic_signred);
                break;
            case MiluoConfig.RISK_HIGH:
                tvRisk.setText("高风险");
                tvRisk.setBackgroundResource(R.drawable.ic_signred);
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pop_card_back:
                mCardPopupWindow.dismiss();
                break;
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
}
