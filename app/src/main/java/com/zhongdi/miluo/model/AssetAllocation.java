package com.zhongdi.miluo.model;

/**
 * Created by libin on 2017/10/10.
 */

public class AssetAllocation {

    /**
     * id : 13077
     * sellFundId : 2217
     * stockPercent : 88.51
     * debtPercent : 5.15
     * cashPercent : 6.11
     * otherPercent :
     * netAsset : 0.00
     * reportDate : 2016-01-30
     * addTime : 2017-09-20
     */

    private String id;
    private String sellFundId;
    private String stockPercent;
    private String debtPercent;
    private String cashPercent;
    private String otherPercent;
    private String netAsset;
    private String reportDate;
    private String addTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellFundId() {
        return sellFundId;
    }

    public void setSellFundId(String sellFundId) {
        this.sellFundId = sellFundId;
    }

    public String getStockPercent() {
        return stockPercent;
    }

    public void setStockPercent(String stockPercent) {
        this.stockPercent = stockPercent;
    }

    public String getDebtPercent() {
        return debtPercent;
    }

    public void setDebtPercent(String debtPercent) {
        this.debtPercent = debtPercent;
    }

    public String getCashPercent() {
        return cashPercent;
    }

    public void setCashPercent(String cashPercent) {
        this.cashPercent = cashPercent;
    }

    public String getOtherPercent() {
        return otherPercent;
    }

    public void setOtherPercent(String otherPercent) {
        this.otherPercent = otherPercent;
    }

    public String getNetAsset() {
        return netAsset;
    }

    public void setNetAsset(String netAsset) {
        this.netAsset = netAsset;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
