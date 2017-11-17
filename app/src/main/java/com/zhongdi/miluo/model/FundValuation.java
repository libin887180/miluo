package com.zhongdi.miluo.model;

/**
 * Created by libin on 2017/11/17.
 */

public class FundValuation {

    /**
     * sellFundId : 2215
     * valuedate : 2017-01-06 00:09:00
     * dayrate : 0.00
     */

    private String sellFundId;
    private String valuedate;
    private String dayrate;

    public String getSellFundId() {
        return sellFundId;
    }

    public void setSellFundId(String sellFundId) {
        this.sellFundId = sellFundId;
    }

    public String getValuedate() {
        return valuedate;
    }

    public void setValuedate(String valuedate) {
        this.valuedate = valuedate;
    }

    public String getDayrate() {
        return dayrate;
    }

    public void setDayrate(String dayrate) {
        this.dayrate = dayrate;
    }
}
