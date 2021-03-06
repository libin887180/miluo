package com.zhongdi.miluo.model;

/**
 * Created by kenn on 2017/10/14.
 */

public class PropertyDetail {


    /**
     * netvalue : 1.46
     * fundtype : 1
     * buystatus : 1
     * avaliableshare : 398959.85
     * fundcode : 000803
     * fundname : 工银研究精选股票
     * risklevel : 3
     * dayincome : 3487052.4
     * yearyld : 0.0
     * dayrate : 0.9
     * marketval : 126405.65
     * fundid : 16419
     * totalshareincome : 998715.8
     * totalshare : 401503.85
     * Accumulatedincome : 998715.8
     * tenthouunitincm : 0.0
     * redeemstatus : 1
     * dividendMethod :1
     */

    private double netvalue;
    private int fundtype;
    private int buystatus;
    private String avaliableshare;
    private String fundcode;
    private String fundname;
    private int risklevel;
    private String dayincome;
    private String yearyld;
    private String dayrate;
    private String marketval;
    private int fundid;
    private String totalshareincome;
    private String totalshare;
    private String Accumulatedincome;
    private String tenthouunitincm;
    private int redeemstatus;
    private String dividendMethod;

    public String getDividendMethod() {
        return dividendMethod;
    }

    public void setDividendMethod(String dividendMethod) {
        this.dividendMethod = dividendMethod;
    }

    public double getNetvalue() {
        return netvalue;
    }

    public void setNetvalue(double netvalue) {
        this.netvalue = netvalue;
    }

    public int getFundtype() {
        return fundtype;
    }

    public void setFundtype(int fundtype) {
        this.fundtype = fundtype;
    }

    public int getBuystatus() {
        return buystatus;
    }

    public void setBuystatus(int buystatus) {
        this.buystatus = buystatus;
    }

    public String getAvaliableshare() {
        return avaliableshare;
    }

    public void setAvaliableshare(String avaliableshare) {
        this.avaliableshare = avaliableshare;
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

    public int getRisklevel() {
        return risklevel;
    }

    public void setRisklevel(int risklevel) {
        this.risklevel = risklevel;
    }


    public int getFundid() {
        return fundid;
    }

    public void setFundid(int fundid) {
        this.fundid = fundid;
    }



    public String getTotalshare() {
        return totalshare;
    }

    public void setTotalshare(String totalshare) {
        this.totalshare = totalshare;
    }

    public String getDayincome() {
        return dayincome;
    }

    public void setDayincome(String dayincome) {
        this.dayincome = dayincome;
    }

    public String getYearyld() {
        return yearyld;
    }

    public void setYearyld(String yearyld) {
        this.yearyld = yearyld;
    }

    public String getDayrate() {
        return dayrate;
    }

    public void setDayrate(String dayrate) {
        this.dayrate = dayrate;
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

    public String getAccumulatedincome() {
        return Accumulatedincome;
    }

    public void setAccumulatedincome(String accumulatedincome) {
        Accumulatedincome = accumulatedincome;
    }

    public String getTenthouunitincm() {
        return tenthouunitincm;
    }

    public void setTenthouunitincm(String tenthouunitincm) {
        this.tenthouunitincm = tenthouunitincm;
    }

    public int getRedeemstatus() {
        return redeemstatus;
    }

    public void setRedeemstatus(int redeemstatus) {
        this.redeemstatus = redeemstatus;
    }
}
