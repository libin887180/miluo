package com.zhongdi.miluo.model;

import java.io.Serializable;

/**
 * Created by kenn on 2017/10/9.
 */

public class FundDetail implements Serializable{

    private String buyStatus ;
    private String dayRate;
    private String discount;
    private String estabDate;
    private String fundCode;
    private String fundManagerName;
    private String fundName;
    private String fundSize;
    private String fundType;
    private String id;
    private String netValue;
    private String rateValue;
    private String status;
    private String valueDate;
    private String yearRate;

    public String getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(String buyStatus) {
        this.buyStatus = buyStatus;
    }

    public String getDayRate() {
        return dayRate;
    }

    public void setDayRate(String dayRate) {
        this.dayRate = dayRate;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getEstabDate() {
        return estabDate;
    }

    public void setEstabDate(String estabDate) {
        this.estabDate = estabDate;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundManagerName() {
        return fundManagerName;
    }

    public void setFundManagerName(String fundManagerName) {
        this.fundManagerName = fundManagerName;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundSize() {
        return fundSize;
    }

    public void setFundSize(String fundSize) {
        this.fundSize = fundSize;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNetValue() {
        return netValue;
    }

    public void setNetValue(String netValue) {
        this.netValue = netValue;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getYearRate() {
        return yearRate;
    }

    public void setYearRate(String yearRate) {
        this.yearRate = yearRate;
    }
}
