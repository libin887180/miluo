package com.zhongdi.miluo.model;

/**
 * Created by libin on 2017/10/12.
 */

public class DealRecord {

    /**
     * item : 100.00元
     * typeitem : 申购
     * transstatus : 确认中
     * fundname : 工银研究精选股票
     * type : 0
     * tradeid : 273
     * tradetime : 2017-10-12  11:30
     */

    private String item;
    private String typeitem;
    private String transstatus;
    private String fundname;
    private String type;
    private int tradeid;
    private String tradetime;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTypeitem() {
        return typeitem;
    }

    public void setTypeitem(String typeitem) {
        this.typeitem = typeitem;
    }

    public String getTransstatus() {
        return transstatus;
    }

    public void setTransstatus(String transstatus) {
        this.transstatus = transstatus;
    }

    public String getFundname() {
        return fundname;
    }

    public void setFundname(String fundname) {
        this.fundname = fundname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTradeid() {
        return tradeid;
    }

    public void setTradeid(int tradeid) {
        this.tradeid = tradeid;
    }

    public String getTradetime() {
        return tradetime;
    }

    public void setTradetime(String tradetime) {
        this.tradetime = tradetime;
    }
}
