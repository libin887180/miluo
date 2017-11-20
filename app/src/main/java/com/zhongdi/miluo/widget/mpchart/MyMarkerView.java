package com.zhongdi.miluo.widget.mpchart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.zhongdi.miluo.R;

import java.util.ArrayList;

/**
 * Created by libin on 2017/10/23.
 */

public class MyMarkerView extends MarkerView {
    private TextView markerTv;
    ArrayList<String> dateList;
    public MyMarkerView(Context context, int layoutResource, ArrayList<String> dateList ) {
        super(context, layoutResource);
        markerTv = (TextView) findViewById(R.id.marker_tv);
        this.dateList = dateList;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        markerTv.setText(dateList.get((int)e.getX())+"");
    }

//    @Override
//    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
//        return new MPPointF(30,-200);
//    }

//    @Override
//    public MPPointF getOffset() {
//        MPPointF mpPointF = new MPPointF(30,-200);
//        return mpPointF;
//    }
    @Override
    public MPPointF getOffset() {
//        tvContent.setBackgroundResource(R.drawable.chart_popu);
        return new MPPointF(getWidth(), -getHeight());
    }

}
