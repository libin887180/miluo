package com.zhongdi.miluo.widget.mpchart;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.zhongdi.miluo.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by libin on 2017/11/20.
 */

public class MiMarkerView extends RelativeLayout implements IMarker {
    private TextView markerTv;
    ArrayList<String> dateList;
    private MPPointF mOffset = new MPPointF();
    private MPPointF mOffset2 = new MPPointF();
    private WeakReference<Chart> mWeakChart;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public MiMarkerView(Context context, int layoutResource, ArrayList<String> dateList ) {
        super(context);
        setupLayoutResource(layoutResource);
        markerTv = (TextView) findViewById(R.id.marker_tv);
        this.dateList = dateList;
    }

    /**
     * Sets the layout resource for a custom MarkerView.
     *
     * @param layoutResource
     */
    private void setupLayoutResource(int layoutResource) {

        View inflated = LayoutInflater.from(getContext()).inflate(layoutResource, this);

        inflated.setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        inflated.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        // measure(getWidth(), getHeight());
        inflated.layout(0, 0, inflated.getMeasuredWidth(), inflated.getMeasuredHeight());
    }

    public void setOffset(MPPointF offset) {
        mOffset = offset;

        if (mOffset == null) {
            mOffset = new MPPointF();
        }
    }

    public void setOffset(float offsetX, float offsetY) {
        mOffset.x = offsetX;
        mOffset.y = offsetY;
    }

    @Override
    public MPPointF getOffset() {
        return mOffset;
    }

    public MPPointF getOffsetRight() {
        return mOffset;
    }

    public void setChartView(Chart chart) {
        mWeakChart = new WeakReference<>(chart);
    }

    public Chart getChartView() {
        return mWeakChart == null ? null : mWeakChart.get();
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {

        Chart chart = getChartView();
        MPPointF offset = getOffset();
        float width = getWidth();
        float height = getHeight();

        mOffset2.x = offset.x;

        if (chart != null && posX + width + mOffset2.x > chart.getWidth()) {
            offset = getOffsetRight();
            mOffset2.x = offset.x;
        }

        mOffset2.y = offset.y;

        if (posX + mOffset2.x < 0) {
            mOffset2.x = - posX;
        } /*else if (chart != null && posX + width + mOffset2.x > chart.getWidth()) {
            mOffset2.x = chart.getWidth() - posX - width;
        }*/

        if (posY + mOffset2.y < 0) {
            mOffset2.y = - posY;
        } else if (chart != null && posY + height + mOffset2.y > chart.getHeight()) {
            mOffset2.y = chart.getHeight() - posY - height;
        }

        return mOffset2;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        markerTv.setText(dateList.get((int)e.getX())+"");
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {

        MPPointF offset = getOffsetForDrawingAtPoint(posX, posY);

        int saveId = canvas.save();
        if((posX + offset.x+getWidth())>canvas.getWidth()){
            canvas.translate(posX - offset.x-getWidth(), posY + offset.y);
        }else{
            canvas.translate(posX + offset.x, posY + offset.y);
        }
        // translate to the correct position and draw
//        canvas.translate(posX + offset.x, posY + offset.y);
        draw(canvas);
        canvas.restoreToCount(saveId);
    }
}
