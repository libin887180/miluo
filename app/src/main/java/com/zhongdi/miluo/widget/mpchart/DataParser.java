package com.zhongdi.miluo.widget.mpchart;

import android.util.SparseArray;

import com.zhongdi.miluo.model.FundValuationResponse;

public class DataParser {
    private FundValuationResponse datas;
    private float baseValue;
    private float permaxmin;
    private float volmax;
    private float min =-12321;
    private float max =12321;
    private SparseArray<String> dayLabels;
    private String code = "sz002081";
    private SparseArray<String> xValuesLabel = new SparseArray<>();

    public void parseData() {
        if (datas.getFundValuation() != null && datas.getFundValuation().size() > 0) {
            for (int i = 0; i < datas.getFundValuation().size(); i++) {
                if(i==0) {
                    min = Float.parseFloat(datas.getFundValuation().get(i).getDayrate());
                    max = Float.parseFloat(datas.getFundValuation().get(i).getDayrate());
                }else{
                    min  = Math.min(min, Float.parseFloat(datas.getFundValuation().get(i).getDayrate()));
                    max  = Math.max(max, Float.parseFloat(datas.getFundValuation().get(i).getDayrate()));
                }
            }
        }

        if (datas.getMarketYieldData() != null && datas.getMarketYieldData().size() > 0) {
            for (int i = 0; i < datas.getMarketYieldData().size(); i++) {
                if(i==0) {
                    if(min==-12321) {
                        min = datas.getMarketYieldData().get(i).getTotalyield();
                    }
                    if(max==12321){
                        max = datas.getMarketYieldData().get(i).getTotalyield();
                    }
                }else{
                    min  = Math.min(min,datas.getMarketYieldData().get(i).getTotalyield());
                    max  = Math.max(max,datas.getMarketYieldData().get(i).getTotalyield());
                }
            }
        }


    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }




    public FundValuationResponse getDatas() {
        return datas;
    }

    public void setDatas(FundValuationResponse datas) {
        this.datas = datas;
    }
}
