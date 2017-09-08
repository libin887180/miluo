package com.zhongdi.miluo.ui.activity.mine;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.mine.TransAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.TransactionDetailPresenter;
import com.zhongdi.miluo.view.TransactionDetailView;
import com.zhongdi.miluo.view.mpchart.MyLineChart;
import com.zhongdi.miluo.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TransationsDetailActivity extends BaseActivity<TransactionDetailPresenter> implements TransactionDetailView {

    @BindView(R.id.line_chart)
    MyLineChart mChart;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private TransAdapter adapter;

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
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setDefaultFooter(BallPulseView.class.getName());
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
                        adapter.addDatas(datas,true);
                        refreshLayout.finishLoadmore();
                    }
                },2000);
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
        adapter = new TransAdapter(mContext, datas);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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
}
