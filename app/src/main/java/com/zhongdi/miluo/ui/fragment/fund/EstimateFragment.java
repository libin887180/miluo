package com.zhongdi.miluo.ui.fragment.fund;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseFragment;
import com.zhongdi.miluo.presenter.EstimateFragPresenter;
import com.zhongdi.miluo.view.EstimateFragmentView;
import com.zhongdi.miluo.widget.mpchart.MyLineChart;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 基金图表Fragment
 * Created by Administrator on 2017/7/24.
 */

public class EstimateFragment extends BaseFragment<EstimateFragPresenter> implements EstimateFragmentView {


    @BindView(R.id.line_chart)
    MyLineChart mChart;

    public static EstimateFragment newInstance(String info) {
        Bundle args = new Bundle();
        EstimateFragment fragment = new EstimateFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected EstimateFragPresenter initPresenter() {
        return new EstimateFragPresenter(this);
    }


    @Override
    protected void initView(View view) {
        mChart.getDescription().setEnabled(false);//描述不可见
//        mChart.setTouchEnabled(false);//不可触摸
        mChart.setDragEnabled(true);//不可拖拽，高亮线
        mChart.setScaleEnabled(false);//不可伸缩
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(45);
        xAxis.setDrawAxisLine(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setXOffset(10f);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setGranularity(50);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawAxisLine(false);

        mChart.getAxisRight().setEnabled(false);
        setData();
        mChart.animateX(1000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_estimate;
    }

    @Override
    public void fetchData() {

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


    @Override
    public void setData() {
        ArrayList<Entry> values = new ArrayList<Entry>();
        ArrayList<Entry> values2 = new ArrayList<Entry>();

        for (int i = 0; i < 40; i++) {

            float val = (float) (Math.random() * 100) + 3;
            values.add(new Entry(i, val));
            values2.add(new Entry(i, val + 30));
        }

        LineDataSet set1;
        LineDataSet set2;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set1.setValues(values);
            set2.setValues(values2);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "DataSet 1");
            set1.setColor(Color.parseColor("#6BAEE9"));
            set1.setLineWidth(2f);
            set1.setHighlightEnabled(true);
            set1.setHighLightColor(Color.parseColor("#FBE1B9"));
            set1.setDrawCircleHole(false);
            set1.setDrawIcons(false);
            set1.setDrawCircles(false);
            set1.setDrawValues(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(values2, "DataSet 2");
            set2.setColor(Color.parseColor("#FF2C40"));
            set2.setLineWidth(2f);
            set2.setHighlightEnabled(true);
            set2.setDrawCircleHole(false);
            set2.setDrawCircles(false);
            set2.setDrawValues(false);
            set2.setHighLightColor(Color.parseColor("#FBE1B9"));
            //set2.setFillFormatter(new MyFillFormatter(900f));
            LineData data = new LineData(set1, set2);

            // set data
            mChart.setData(data);
        }
    }

}