package com.zhongdi.miluo.ui.activity.mine;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.TransactionDetailPresenter;
import com.zhongdi.miluo.view.TransactionDetailView;
import com.zhongdi.miluo.view.mpchart.MyLineChart;

import java.util.ArrayList;

import butterknife.BindView;

public class TransationsDetailActivity extends BaseActivity<TransactionDetailPresenter> implements TransactionDetailView {

    @BindView(R.id.line_chart)
    MyLineChart mChart;

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
    public void setData() {
        ArrayList<Entry> values = new ArrayList<Entry>();
        ArrayList<Entry> values2 = new ArrayList<Entry>();

        for (int i = 0; i < 40; i++) {

            float val = (float) (Math.random() * 100) + 3;
            values.add(new Entry(i, val));
            values2.add(new Entry(i, val + 30));
        }

        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
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
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            set1.setDrawFilled(true);
            if(Utils.getSDKInt()>=18){
                Drawable drawable = ContextCompat.getDrawable(mContext,R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            }else{
                set1.setFillColor(Color.parseColor("70FFE9EB"));
            }
            LineData data = new LineData(set1);

            // set data
            mChart.setData(data);
        }
    }

}
