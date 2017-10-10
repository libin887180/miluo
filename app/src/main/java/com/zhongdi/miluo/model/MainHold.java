package com.zhongdi.miluo.model;

/**
 * Created by libin on 2017/10/10.
 */

public class MainHold {

    /**
     * stockCode :
     * stockName : 华测检测
     * stockChangePercent : 0.00
     * newPercent : 5.23
     */

    private String stockCode;
    private String stockName;
    private String stockChangePercent;
    private String newPercent;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockChangePercent() {
        return stockChangePercent;
    }

    public void setStockChangePercent(String stockChangePercent) {
        this.stockChangePercent = stockChangePercent;
    }

    public String getNewPercent() {
        return newPercent;
    }

    public void setNewPercent(String newPercent) {
        this.newPercent = newPercent;
    }
}
