package com.zhongdi.miluo.ui.fragment.fund;

import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseFragment;
import com.zhongdi.miluo.model.FundValuationResponse;
import com.zhongdi.miluo.presenter.HalfYearFragPresenter;
import com.zhongdi.miluo.view.HalfYearFragmentView;
import com.zhongdi.miluo.widget.mpchart.DataParser;
import com.zhongdi.miluo.widget.mpchart.MiLuoLineChart;
import com.zhongdi.miluo.widget.mpchart.MiMarkerView;
import com.zhongdi.miluo.widget.mpchart.MyXAxis;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 基金图表Fragment
 * Created by Administrator on 2017/7/24.
 */

public class HalfYearLineFragment extends BaseFragment<HalfYearFragPresenter> implements HalfYearFragmentView {

    @BindView(R.id.line_chart)
    MiLuoLineChart lineChart;
    String sellFundId;
    private YAxis axisLeft;
    private DataParser mData1;
    private LineDataSet d1;
    private LineDataSet d2;
    MyXAxis xAxisLine;
    @BindView(R.id.tv_fund_value)
    TextView tvFundValue;
    @BindView(R.id.tv_hs300_value)
    TextView tvHs300Value;
    private float mIndex;

    public static HalfYearLineFragment newInstance(String sellFundId) {
        Bundle args = new Bundle();
        HalfYearLineFragment fragment = new HalfYearLineFragment();
        args.putString("sellFundId", sellFundId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        if(lineChart==null||lineChart.isEmpty()){
            presenter = initPresenter();
            prepareFetchData(true);
        }
    }
    @Override
    protected HalfYearFragPresenter initPresenter() {
        return new HalfYearFragPresenter(this);
    }


    @Override
    protected void initView(View view) {
        initChart();
    }

    private void initChart() {

        lineChart.setScaleEnabled(false);
        lineChart.setDrawBorders(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getDescription().setEnabled(false);//描述不可见
        lineChart.setNoDataText("数据处理中");
        Legend lineChartLegend = lineChart.getLegend();
        lineChartLegend.setEnabled(false);
        //x轴
        xAxisLine = lineChart.getXAxis();
        xAxisLine.setDrawLabels(true);
        xAxisLine.setDrawGridLines(false);
        xAxisLine.setLabelCount(2);
        xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisLine.setTextSize(15);
        xAxisLine.setTextColor(getResources().getColor(R.color.text_color_normal));
        xAxisLine.setYOffset(10);
        //x轴
//        xAxis = lineChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setLabelsToSkip(59);


        //左边y
        axisLeft = lineChart.getAxisLeft();
        axisLeft.setLabelCount(5, true);
        axisLeft.setDrawLabels(true);
        axisLeft.setDrawGridLines(true);
        axisLeft.setTextSize(15);
        axisLeft.setTextColor(getResources().getColor(R.color.text_color_normal));
//        axisLeft.setXOffset(10f);
        axisLeft.setGranularityEnabled(true);
        axisLeft.setDrawZeroLine(false);
        axisLeft.setDrawAxisLine(false);
        //y轴样式
        this.axisLeft.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00");
                return mFormat.format(value) + "%";
            }

        });

        //背景线
        this.xAxisLine.setGridColor(getResources().getColor(R.color.divider_list));
        this.xAxisLine.setGridLineWidth(1);
        this.xAxisLine.setAxisLineColor(getResources().getColor(R.color.divider_list));
        this.xAxisLine.setAxisLineWidth(1);
//        this.axisLeft.setGridColor(getResources().getColor(R.color.minute_zhoutv));
//        this.axisRight.setAxisLineColor(getResources().getColor(R.color.minute_zhoutv));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_estimate;
    }

    @Override
    public void fetchData() {
        sellFundId = getArguments().getString("sellFundId");
        presenter.getFundVal(sellFundId);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, rootView);//同样把 ButterKnife 抽出来
            initView(rootView);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，
            // 要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }


    public void setShowLabels(SparseArray<String> labels) {
        xAxisLine.setXLabels(labels);
    }

    private SparseArray<String> setXLabels(DataParser mData) {
        int size = mData.getDatas().getMarketYieldData().size();
        SparseArray<String> xLabels = new SparseArray<>();
        xLabels.put(0, mData.getDatas().getMarketYieldData().get(0).getValuedate());
        xLabels.put(size - 1, mData.getDatas().getMarketYieldData().get(size - 1).getValuedate());
        return xLabels;
    }

    public void setData(DataParser mData) {
        if (mData.getDatas().getMarketYieldData().size() == 0 && mData.getDatas().getFundValuation().size() == 0) {
            lineChart.setNoDataText("暂无数据");
            return;
        }

        setShowLabels(setXLabels(mData));
        //设置y左右两轴最大最小值
//        axisLeft.setAxisMinValue(mData.getMin() * 1.2f);
//        axisLeft.setAxisMaxValue(mData.getMax() * 1.2f);
//        axisRight.setAxisMinValue(mData.getPercentMin());
//        axisRight.setAxisMaxValue(mData.getPercentMax());


        final ArrayList<Entry> HS300 = new ArrayList<Entry>();
        ArrayList<String> hsMarketViews = new ArrayList<String>();
        ArrayList<String> xVals = new ArrayList<String>();

        final ArrayList<Entry> fundData = new ArrayList<Entry>();
        ArrayList<String> fundMarkViews = new ArrayList<String>();

        if (mData.getDatas().getMarketYieldData().size() > 0) {
            for (int i = 0; i < mData.getDatas().getMarketYieldData().size(); i++) {
                HS300.add(new Entry(i, (float) mData.getDatas().getMarketYieldData().get(i).getTotalyield()));
                hsMarketViews.add(mData.getDatas().getMarketYieldData().get(i).getValuedate());
            }
        }
        d1 = new LineDataSet(HS300, "沪深300");

        d1.setColor(Color.parseColor("#6BAEE9"));
        d1.setLineWidth(2f);
        d1.setHighlightEnabled(true);
        d1.setHighLightColor(Color.parseColor("#FBE1B9"));
        d1.setDrawCircleHole(false);
        d1.setDrawCircles(false);
        d1.setDrawValues(false);
        //谁为基准
        d1.setAxisDependency(YAxis.AxisDependency.LEFT);

        if (mData.getDatas().getFundValuation().size() > 0) {
            for (int i = 0; i < mData.getDatas().getFundValuation().size(); i++) {
                fundData.add(new Entry(i, Float.parseFloat(mData.getDatas().getFundValuation().get(i).getDayrate())));
                fundMarkViews.add(mData.getDatas().getFundValuation().get(i).getValuedate());
            }
        }

        d2 = new LineDataSet(fundData, "本基金");
        d2.setColor(Color.parseColor("#FF2C40"));
        d2.setLineWidth(2f);
        d2.setHighlightEnabled(true);
        d2.setDrawCircleHole(false);
        d2.setDrawCircles(false);
        d2.setDrawValues(false);
        d2.setHighLightColor(Color.parseColor("#FBE1B9"));
        //谁为基准
        d2.setAxisDependency(YAxis.AxisDependency.LEFT);

        LineData cd = new LineData(d1, d2);
//        setMarkerView(dateList);
        setMarkerView(hsMarketViews);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                float index = e.getX();
                mIndex = index;
                if (fundData.size() - 1 >= (int) index && fundData.get((int) index) != null) {
                    tvFundValue.setText(fundData.get((int) index).getY() + "%");
                } else {
                    tvFundValue.setText("--");
                }
                if (HS300.size() - 1 >= (int) index && HS300.get((int) index) != null) {
                    tvHs300Value.setText(HS300.get((int) index).getY() + "%");
                } else {
                    tvHs300Value.setText("--");
                }
            }

            @Override
            public void onNothingSelected() {
                // 再次点击时调用这个, 要不非高亮
//                lineChart.highlightValue(mIndex, 0);

            }
        });
        lineChart.setData(cd);
        lineChart.setDrawMarkers(true);
        lineChart.invalidate();//刷新图
    }

    public String[] getMinutesCount(DataParser mData) {
        return new String[mData.getDatas().getMarketYieldData().size()];
    }

    private void setMarkerView(ArrayList<String> dateList) {
        MiMarkerView myMarkerView = new MiMarkerView(getActivity(), R.layout.mymarkerview, dateList);
//        lineChart.setMarkerView(myMarkerView);
        myMarkerView.setOffset(30, -200);
        lineChart.setMarker(myMarkerView);
    }

    @Override
    public void onDataSuccess(FundValuationResponse response) {
        mData1 = new DataParser();
        mData1.setDatas(response);
        mData1.parseData();
        setData(mData1);
        lineChart.animateX(1000);
    }
}