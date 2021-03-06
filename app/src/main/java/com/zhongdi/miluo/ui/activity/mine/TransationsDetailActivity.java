package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
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
import com.vise.log.ViseLog;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.FenhongTypeAdapter;
import com.zhongdi.miluo.adapter.mine.TransAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.eventbus.MessageEvent;
import com.zhongdi.miluo.model.DealRecord;
import com.zhongdi.miluo.model.ProfitLineBean;
import com.zhongdi.miluo.model.PropertyDetail;
import com.zhongdi.miluo.presenter.TransactionDetailPresenter;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.ui.activity.market.BuyFundActivity;
import com.zhongdi.miluo.ui.activity.market.FundCurrencyDetailActivity;
import com.zhongdi.miluo.ui.activity.market.FundDetailActivity;
import com.zhongdi.miluo.ui.activity.market.SellFundActivity;
import com.zhongdi.miluo.view.TransactionDetailView;
import com.zhongdi.miluo.widget.OnPasswordInputFinish;
import com.zhongdi.miluo.widget.PayView;
import com.zhongdi.miluo.widget.RecycleViewDivider;
import com.zhongdi.miluo.widget.mpchart.MyCustomFormatter;
import com.zhongdi.miluo.widget.mpchart.MyLineChart;
import com.zhongdi.miluo.widget.mpchart.MyXAxis;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    MyXAxis xAxis;
    private YAxis leftAxis;
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
    private int fundtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fundcode = getIntent().getStringExtra("fundcode");
        fundtype = getIntent().getIntExtra("fundType", -1);
        binding(R.layout.activity_transaction_detail);
        EventBus.getDefault().register(this);
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
        pageIndex = 1;
        presenter.getTradRecords(pageIndex, MiluoConfig.DEFAULT_PAGESIZE, fundcode);
    }

    @Override
    public void modifyBonusFailed() {
        presenter.getPropertyDetail(fundcode);
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void reLogin() {
        if (MyApplication.getInstance().islogignShow) {
            ViseLog.i("登录已显示");
        } else {
            MyApplication.getInstance().islogignShow = true;
            Intent intent = new Intent(mContext, QuickLoginActivity.class);
            startActivity(intent);
            ViseLog.e("登录未显示");
        }
    }

    @Override
    public void setLineData(List<ProfitLineBean> profitLineBeans) {
        setChartData(profitLineBeans);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        ViseLog.i("******");
        presenter.getPropertyDetail(fundcode);
        if ((fundtype + "").equals(MiluoConfig.HUOBI) || (fundtype + "").equals(MiluoConfig.DUANQI)) {

        } else {
            if (mChart.isEmpty()) {
                presenter.getLines(fundcode);
            }
        }


        pageIndex = 1;
        presenter.getTradRecords(pageIndex, MiluoConfig.DEFAULT_PAGESIZE, fundcode);
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
        adapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<DealRecord>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, DealRecord dealRecord, int position) {

                if (dealRecord.getTypeitem().contains("分红")) {
                    return;
                } else {
                    Intent intent = new Intent(mContext, TransationsRecordActivity.class);
                    intent.putExtra("tradeid", dealRecord.getTradeid() + "");
                    intent.putExtra("tradType", dealRecord.getType());//type (integer): 交易类型0申购，1赎回
                    startActivity(intent);
                }

            }
        });

        presenter.getPropertyDetail(fundcode);
        if ((fundtype + "").equals(MiluoConfig.HUOBI) || (fundtype + "").equals(MiluoConfig.DUANQI)) {
            mChart.setVisibility(View.GONE);
        } else {
            setUpChart();

        }
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

    public void setChartData(List<ProfitLineBean> profitLineBeans) {
        ArrayList<Entry> lines = new ArrayList<Entry>();
        ArrayList<Entry> buy = new ArrayList<Entry>();
        ArrayList<Entry> sell = new ArrayList<Entry>();
//        for (int i = 0; i < 40; i++) {
//
//            float val = (float) (Math.random() * 100) + 3;
//            values.add(new Entry(i, val));
//            if (i == 30) {
//                values2.add(new Entry(i, val));
//            }
//            if (i == 20) {
//                values3.add(new Entry(i, val));
//            }
//        }
        for (int i = 0; i < profitLineBeans.size(); i++) {
            lines.add(new Entry(i, (float) profitLineBeans.get(i).getNetvalue()));
            if (profitLineBeans.get(i).getType().equals("1")) {
                buy.add((new Entry(i, (float) profitLineBeans.get(i).getNetvalue())));
            } else if (profitLineBeans.get(i).getType().equals("2")) {
                sell.add((new Entry(i, (float) profitLineBeans.get(i).getNetvalue())));
            }
        }
        LineDataSet set1;
        LineDataSet set2;
        LineDataSet set3;


        xAxis = mChart.getXAxis();
        SparseArray<String> xLabels = new SparseArray<>();
        xLabels.put(0, profitLineBeans.get(0).getValuedate());
        xLabels.put(profitLineBeans.size() - 1, profitLineBeans.get(profitLineBeans.size() - 1).getValuedate());
        xAxis.setXLabels(xLabels);
        xAxis.setValueFormatter(new MyCustomFormatter(profitLineBeans));

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(lines);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set2.setValues(buy);
            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set3.setValues(sell);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(lines, "DataSet 1");
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
            set2 = new LineDataSet(buy, "DataSet 2");
            set2.setColor(Color.TRANSPARENT);
            set2.setLineWidth(2f);
            set2.setHighlightEnabled(true);
            set2.setDrawCircleHole(false);
            set2.setDrawIcons(false);
            set2.setDrawCircles(true);
            set2.setCircleColor(Color.parseColor("#FF2C40"));
            set2.setDrawValues(false);
            set2.setDrawFilled(false);


            set3 = new LineDataSet(sell, "DataSet 3");
            set3.setColor(Color.TRANSPARENT);
            set3.setLineWidth(2f);
            set3.setHighlightEnabled(true);
            set3.setDrawCircleHole(false);
            set3.setDrawIcons(false);
            set3.setDrawCircles(true);
            set3.setCircleColor(Color.parseColor("#6BAEE9"));
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
                buyIntent.putExtra(IntentConfig.MAIDIAN, IntentConfig.Trade_Details_Apply);
                startActivity(buyIntent);
                break;
            case R.id.tv_sell:
                Intent intent = new Intent(mContext, SellFundActivity.class);
                intent.putExtra("fundCode", fundcode);
                intent.putExtra(IntentConfig.SOURCE, IntentConfig.Trade_Details_Redeem);
                startActivity(intent);
                break;
            case R.id.ll_fenhong:
                if (tvFenhong.getText().toString().contains("确认中")) {
                    return;
                } else {
                    if ((fundtype + "").equals(MiluoConfig.HUOBI) || (fundtype + "").equals(MiluoConfig.DUANQI)) {
                        return;
                    } else {
                        showFenHongPopupWindow();
                    }
                }
                break;
            case R.id.rl_fund_info:
                if (!TextUtils.isEmpty(fundId)) {
                    Intent detail;
                    if ((fundtype + "").equals(MiluoConfig.HUOBI) || (fundtype + "").equals(MiluoConfig.DUANQI)) {
                        detail = new Intent(mContext, FundCurrencyDetailActivity.class);
                    } else {
                        detail = new Intent(mContext, FundDetailActivity.class);

                    }
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

        xAxis = mChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(2);
        xAxis.setDrawAxisLine(true);
        xAxis.setTextSize(15);
        xAxis.setTextColor(getResources().getColor(R.color.text_color_normal));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setXOffset(10f);
        leftAxis.setLabelCount(5);
//        leftAxis.setDrawTopYLabelEntry(false);
        leftAxis.setDrawZeroLine(false);
//        leftAxis.setDrawAxisLine(false);//Y轴坐标
//        leftAxis.setGranularityEnabled(true);
        mChart.getAxisRight().setEnabled(false);
        leftAxis.setTextSize(15);
        leftAxis.setTextColor(getResources().getColor(R.color.text_color_normal));


        //背景线
        xAxis.setGridColor(getResources().getColor(R.color.divider_list));
        xAxis.setGridLineWidth(1);
        leftAxis.setAxisLineColor(getResources().getColor(R.color.divider_list));
        leftAxis.setAxisLineWidth(1);

        Legend legend = mChart.getLegend();
        legend.setEnabled(false);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        legend.setYOffset(20f);

        mChart.animateX(1000);
        presenter.getLines(fundcode);
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
        fundtype = body.getFundtype();
        if ((body.getFundtype() + "").equals(MiluoConfig.HUOBI) || (body.getFundtype() + "").equals(MiluoConfig.DUANQI)) {
            tvIncreaseType.setText("七日年化");
            tvNetvalueType.setText("万分收益");
            tvNetvalue.setText(body.getTenthouunitincm() + "");
            if (body.getYearyld().contains("-")) {
                tvDayrate.setTextColor(mContext.getResources().getColor(R.color.increase_green));
            } else {
                tvDayrate.setTextColor(mContext.getResources().getColor(R.color.red));
            }
            tvDayrate.setText(body.getYearyld() + "%");
        } else {
            tvIncreaseType.setText("日涨幅");
            tvNetvalueType.setText("最新净值");
            tvNetvalue.setText(body.getNetvalue() + "");
            tvDayrate.setText(body.getDayrate() + "%");
        }
        if (TextUtils.equals(body.getDividendMethod(), "0")) {
            listAdapter.setCheck(0);
            tvFenhong.setText("红利再投");
        } else if (TextUtils.equals(body.getDividendMethod(), "1")) {
            listAdapter.setCheck(1);
            tvFenhong.setText("现金分红");
        } else if (TextUtils.equals(body.getDividendMethod(), "2")) {
            tvFenhong.setText("红利再投\n确认中");
        } else {
            tvFenhong.setText("现金分红\n确认中");
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
