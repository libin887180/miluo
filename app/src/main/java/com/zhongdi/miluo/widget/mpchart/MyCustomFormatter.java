package com.zhongdi.miluo.widget.mpchart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.zhongdi.miluo.model.ProfitLineBean;

import java.util.List;

/**
 * Created by libin on 2017/11/30.
 */

public class MyCustomFormatter implements IAxisValueFormatter {

    private List<ProfitLineBean>  mValues;

    public MyCustomFormatter(List<ProfitLineBean> values) {
        this.mValues = values;
    }
    private static final String TAG = "MyXFormatter";

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
//        ViseLog.d(TAG, "----->getFormattedValue: "+value);
        return mValues.get((int)value).getValuedate();
    }
}
