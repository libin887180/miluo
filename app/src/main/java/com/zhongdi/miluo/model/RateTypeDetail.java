package com.zhongdi.miluo.model;

import java.util.List;

/**
 * Created by libin on 2017/10/11.
 */

public class RateTypeDetail {
    /**
     * rateType : 00
     * rateName : 前端认购
     * data : [{"id":"53467","sellFundId":"2217","minsubscribeamt":"","manageRate":"","custodyRate":"","rateType":"00","rateTypeName":"前端认购","amountDownLimit":"0","amountUpLimit":"50","rateValue":"1.00%","amountDesc":"购买金额<50万","discount":""},{"id":"53469","sellFundId":"2217","minsubscribeamt":"","manageRate":"","custodyRate":"","rateType":"00","rateTypeName":"前端认购","amountDownLimit":"50","amountUpLimit":"200","rateValue":"0.50%","amountDesc":"50万≤购买金额<200万","discount":""},{"id":"53471","sellFundId":"2217","minsubscribeamt":"","manageRate":"","custodyRate":"","rateType":"00","rateTypeName":"前端认购","amountDownLimit":"200","amountUpLimit":"500","rateValue":"0.10%","amountDesc":"200万≤购买金额<500万","discount":""},{"id":"53473","sellFundId":"2217","minsubscribeamt":"","manageRate":"","custodyRate":"","rateType":"00","rateTypeName":"前端认购","amountDownLimit":"500","amountUpLimit":"99999997952","rateValue":"1000元/笔","amountDesc":"购买金额≥500万","discount":""}]
     */

    private String rateType;
    private String rateName;
    private List<RateDetail> data;

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public List<RateDetail> getData() {
        return data;
    }

    public void setData(List<RateDetail> data) {
        this.data = data;
    }

}
