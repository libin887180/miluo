package com.zhongdi.miluo.model;

/**
 * Created by kenn on 2017/10/21.
 */

public class HistoryValue {

    /**
     * cumValue : string
     * dayRate : string
     * netValue : string
     * sellFundId : string
     * valueDate : string
     */

    private String cumValue;
    private String dayRate;
    private String netValue;
    private String sellFundId;
    private String valueDate;
    private String tenthouunitincm ;
    private String yearyld ;


    public String getTenthouunitincm() {
        return tenthouunitincm;
    }

    public void setTenthouunitincm(String tenthouunitincm) {
        this.tenthouunitincm = tenthouunitincm;
    }

    public String getYearyld() {
        return yearyld;
    }

    public void setYearyld(String yearyld) {
        this.yearyld = yearyld;
    }

    public String getCumValue() {
        return cumValue;
    }

    public void setCumValue(String cumValue) {
        this.cumValue = cumValue;
    }

    public String getDayRate() {
        return dayRate;
    }

    public void setDayRate(String dayRate) {
        this.dayRate = dayRate;
    }

    public String getNetValue() {
        return netValue;
    }

    public void setNetValue(String netValue) {
        this.netValue = netValue;
    }

    public String getSellFundId() {
        return sellFundId;
    }

    public void setSellFundId(String sellFundId) {
        this.sellFundId = sellFundId;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }
}
