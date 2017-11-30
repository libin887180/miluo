package com.zhongdi.miluo.widget.mpchart;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.renderer.LineChartRenderer;

/**
 * Created by libin on 2017/8/30.
 */

public class MyLineChart extends BarLineChartBase<LineData> implements LineDataProvider {

    public MyLineChart(Context context) {
        super(context);
    }

    public MyLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        mXAxis = new MyXAxis();
//        mAxisLeft = new MyYAxis(YAxis.AxisDependency.LEFT);
        mXAxisRenderer = new MyXAxisRenderer(mViewPortHandler, (MyXAxis) mXAxis, mLeftAxisTransformer, this);
//        mAxisRendererLeft = new MyYAxisRenderer(mViewPortHandler, (MyYAxis) mAxisLeft, mLeftAxisTransformer);
        mRenderer = new MyLineChartRenderer(this, mAnimator, mViewPortHandler);
    }

//    /*返回转型后的左右轴*/
//    @Override
//    public MyYAxis getAxisLeft() {
//        return (MyYAxis) super.getAxisLeft();
//    }

    @Override
    public MyXAxis getXAxis() {
        return (MyXAxis) super.getXAxis();
    }
    @Override
    public LineData getLineData() {
        return mData;
    }

    @Override
    protected void onDetachedFromWindow() {
        // releases the bitmap in the renderer to avoid oom error
        if (mRenderer != null && mRenderer instanceof LineChartRenderer) {
            ((LineChartRenderer) mRenderer).releaseBitmap();
        }
        super.onDetachedFromWindow();
    }
}
