package com.zhongdi.miluo.model;

/**
 * Created by libin on 2017/10/12.
 */

public class HomeAssetBean {

    /**
     * fundtype : 1
     * marketval : 100.0
     * totalshareincome : 0.0
     * fundcode : 000803
     * fundname : 工银研究精选股票
     * transamount : 10000
     * tradeid : 111
     * status : 申购中
     */
    private String fundid;
    private int fundtype;
    private String marketval;
    private String totalshareincome;
    private String fundcode;
    private String fundname;
    private String transamount;
    private String tradeid;
    private String status;

    public String getFundid() {
        return fundid;
    }

    public void setFundid(String fundid) {
        this.fundid = fundid;
    }

    public int getFundtype() {
        return fundtype;
    }

    public void setFundtype(int fundtype) {
        this.fundtype = fundtype;
    }

    public String getMarketval() {
        return marketval;
    }

    public void setMarketval(String marketval) {
        this.marketval = marketval;
    }

    public String getTotalshareincome() {
        return totalshareincome;
    }

    public void setTotalshareincome(String totalshareincome) {
        this.totalshareincome = totalshareincome;
    }

    public String getFundcode() {
        return fundcode;
    }

    public void setFundcode(String fundcode) {
        this.fundcode = fundcode;
    }

    public String getFundname() {
        return fundname;
    }

    public void setFundname(String fundname) {
        this.fundname = fundname;
    }

    public String getTransamount() {
        return transamount;
    }

    public void setTransamount(String transamount) {
        this.transamount = transamount;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
