package com.zhongdi.miluo.model;

/**
 * Created by libin on 2017/11/17.
 */

public class FundValuation {


    /**
     * fundcode :
     * fundname :
     * fundtype :
     * valuedate : 20170307
     * netvalue : 1.036
     * cumvalue : 1.176
     * totalrate : 0
     */

    private String fundcode;
    private String fundname;
    private String fundtype;
    private String valuedate;
    private double netvalue;
    private double cumvalue;
    private String totalrate;

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

    public String getFundtype() {
        return fundtype;
    }

    public void setFundtype(String fundtype) {
        this.fundtype = fundtype;
    }

    public String getValuedate() {
        return valuedate;
    }

    public void setValuedate(String valuedate) {
        this.valuedate = valuedate;
    }

    public double getNetvalue() {
        return netvalue;
    }

    public void setNetvalue(double netvalue) {
        this.netvalue = netvalue;
    }

    public double getCumvalue() {
        return cumvalue;
    }

    public void setCumvalue(double cumvalue) {
        this.cumvalue = cumvalue;
    }

    public String getTotalrate() {
        return totalrate;
    }

    public void setTotalrate(String totalrate) {
        this.totalrate = totalrate;
    }
}
