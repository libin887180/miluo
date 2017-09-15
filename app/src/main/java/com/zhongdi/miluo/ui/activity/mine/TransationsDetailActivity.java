package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.zhongdi.miluo.presenter.TransactionDetailPresenter;
import com.zhongdi.miluo.ui.activity.market.BuyFundActivity;
import com.zhongdi.miluo.ui.activity.market.SellFundActivity;
import com.zhongdi.miluo.view.TransactionDetailView;
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
    private TransAdapter adapter;
    private RecyclerView fenhongRv;
    private  FenhongTypeAdapter listAdapter;
    private PopupWindow mCardPopupWindow;
    private View fenhongPopView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_transaction_detail);
    }

    @Override
    protected TransactionDetailPresenter initPresenter() {
        return new TransactionDetailPresenter(this);
    }

    @Override
    protected void initialize() {
        setupCardPopupWindow();
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setEnableOverScroll(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refresh) {

                super.onLoadMore(refreshLayout);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> datas = new ArrayList<>();
                        datas.add("股票");
                        datas.add("债券");
                        datas.add("保本");
                        datas.add("保本");
                        datas.add("保本");
                        datas.add("保本");
                        datas.add("保本");
                        adapter.addDatas(datas, true);
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
        setUpChart();
        List<String> datas = new ArrayList<>();
        datas.add("股票");
        datas.add("债券");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        adapter = new TransAdapter(mContext, datas);
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
    }

    public void setData() {
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

    @OnClick({R.id.tv_sell, R.id.tv_buy, R.id.ll_fenhong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_buy:
                startActivity(new Intent(mContext, BuyFundActivity.class));
                break;
            case R.id.tv_sell:
                startActivity(new Intent(mContext, SellFundActivity.class));
                break;
            case R.id.ll_fenhong:

                showPswPopupWindow();
                break;
        }
    }
    private void showPswPopupWindow() {
        mCardPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
    // 显示弹窗
    public void setupCardPopupWindow() {
        // 初始化弹窗
        fenhongPopView = View.inflate(this, R.layout.pop_card_list_view, null);
        mCardPopupWindow = new PopupWindow(fenhongPopView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        fenhongPopView.findViewById(R.id.gray_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardPopupWindow.dismiss();
            }
        });
        fenhongPopView.findViewById(R.id.tv_pop_card_back).setOnClickListener(this);
        fenhongRv = fenhongPopView.findViewById(R.id.rl_card_list);
        List<String> datas = new ArrayList<>();
        datas.add("红利再投");
        datas.add("现金分红");
        listAdapter = new FenhongTypeAdapter(mContext, datas);
        fenhongRv.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        fenhongRv.setLayoutManager(new LinearLayoutManager(mContext));
        fenhongRv.setAdapter(listAdapter);
        listAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                listAdapter.setCheck(position);
                mCardPopupWindow.dismiss();
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
        setData();
        Legend legend = mChart.getLegend();

        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setYOffset(20f);
        mChart.animateX(1000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_pop_card_back:
                mCardPopupWindow.dismiss();
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
