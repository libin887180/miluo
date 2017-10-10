package com.zhongdi.miluo.model;

/**
 * Created by kenn on 2017/10/9.
 * 基金档案
 */

public class Archives {


    /**
     * id : 12419
     * fundCode : 150290
     * fundName : 中融中证煤炭指数分级证券投资基金
     * fundType : 1
     * estabDate : 2015-01-25
     * minredemptionvol : 0.01
     * fundSize : 11.38
     * fundManagerName : 中融基金
     * custodian : 海通证券
     * fundManager : 赵菲
     * lastTime : 2017-09-22
     */

    private String id;
    private String fundCode;
    private String fundName;
    private String fundType;
    private String estabDate;
    private String minredemptionvol;
    private String fundSize;
    private String fundManagerName;
    private String custodian;
    private String fundManager;
    private String lastTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getEstabDate() {
        return estabDate;
    }

    public void setEstabDate(String estabDate) {
        this.estabDate = estabDate;
    }

    public String getMinredemptionvol() {
        return minredemptionvol;
    }

    public void setMinredemptionvol(String minredemptionvol) {
        this.minredemptionvol = minredemptionvol;
    }

    public String getFundSize() {
        return fundSize;
    }

    public void setFundSize(String fundSize) {
        this.fundSize = fundSize;
    }

    public String getFundManagerName() {
        return fundManagerName;
    }

    public void setFundManagerName(String fundManagerName) {
        this.fundManagerName = fundManagerName;
    }

    public String getCustodian() {
        return custodian;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public String getFundManager() {
        return fundManager;
    }

    public void setFundManager(String fundManager) {
        this.fundManager = fundManager;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
