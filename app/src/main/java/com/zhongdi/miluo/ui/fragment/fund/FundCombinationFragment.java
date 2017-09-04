package com.zhongdi.miluo.ui.fragment.fund;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fingdo.statelayout.StateLayout;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.market.MainHoldAdapter;
import com.zhongdi.miluo.widget.NOScollListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class FundCombinationFragment extends Fragment {
    Unbinder unbinder;
    View rootView;
    @BindView(R.id.chart1)
    PieChart mChart;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    @BindView(R.id.lv_main_hold)
    NOScollListView lvMainHold;
    Unbinder unbinder1;

    public static FundCombinationFragment newInstance(String info) {
        Bundle args = new Bundle();
        FundCombinationFragment fragment = new FundCombinationFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_fund_combination, null);
            unbinder = ButterKnife.bind(this, rootView);
            initialize();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，
            // 要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            unbinder = ButterKnife.bind(this, rootView);
        }
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initialize() {
        initPieChart();
        initListView();
    }

    private void initListView() {
        lvMainHold.setFocusable(false);
        MainHoldAdapter mainHoldAdapter = new MainHoldAdapter(getActivity());
        lvMainHold.setAdapter(mainHoldAdapter);

        List<String> datas = new ArrayList<>();
        datas.add("111");
        datas.add("222");
        datas.add("333");
        datas.add("444");
        datas.add("444");
        datas.add("444");
        datas.add("444");
        datas.add("444");

        mainHoldAdapter.setDataList(datas);
    }

    private void initPieChart() {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);   //设置pieChart图表上下左右的偏移，类似于外边距

        mChart.setDragDecelerationFrictionCoef(0.95f);//设置pieChart图表转动阻力摩擦系数[0,1]


        mChart.setDrawHoleEnabled(true);  //是否显示PieChart内部圆环(true:下面属性才有意义)
        mChart.setHoleColor(Color.WHITE);//设置PieChart内部圆的颜色

        mChart.setTransparentCircleColor(Color.WHITE);//设置PieChart内部透明圆与内部圆间距(31f-28f)填充颜色
        mChart.setTransparentCircleAlpha(110);//设置PieChart内部透明圆与内部圆间距(31f-28f)透明度[0~255]数值越小越透明

        mChart.setHoleRadius(58f);//设置PieChart内部圆的半径(这里设置28.0f)
        mChart.setTransparentCircleRadius(61f);//设置PieChart内部透明圆的半径(这里设置31.0f)

        mChart.setDrawCenterText(true); //是否绘制PieChart内部中心文本（true：下面属性才有意义）
        mChart.setCenterText(generateCenterSpannableText()); //设置PieChart内部圆文字的内容
        mChart.setCenterTextSize(13f);
        mChart.setRotationAngle(0);//设置pieChart图表起始角度
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);//设置pieChart图表是否可以手动旋转
        mChart.setHighlightPerTapEnabled(true);   //设置piecahrt图表点击Item高亮是否可用

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//        mChart.setOnChartValueSelectedListener(this);

        setData(4, 100);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setXOffset(30f);
        l.setTextSize(16f);
        l.setFormSize(16f);
        l.setYEntrySpace(10f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setDrawEntryLabels(false);//设置pieChart是否只显示饼图上百分比不显示文字（true：下面属性才有效果）
        mChart.setEntryLabelColor(Color.WHITE); //设置pieChart图表文本字体颜色
        mChart.setEntryLabelTextSize(12f); //设置pieChart图表文本字体大小


    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("1.95亿\n基金规模");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 5, 0);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F51")), 0, 5, 0);
        s.setSpan(new RelativeSizeSpan(1f), 5, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 5, s.length(), 0);
        return s;
    }

    private void setData(int count, float range) {

        float mult = range;
        String[] mParties = new String[]{"Party A", "Party B", "Party C", "Party D",};
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    mParties[i % mParties.length]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);
        dataSet.setDrawValues(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
        super.onDestroyView();
        unbinder1.unbind();

    }
}