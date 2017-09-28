package com.zhongdi.miluo.model;

import java.io.Serializable;

/**
 * Created by kenn on 2017/9/27.
 */

public class BankInfo implements Serializable{


    private String amtdesc;
    private String bankcode;
    private String bankname;
    private String bankno;
    private String biztype;
    private int dateamt;//单日累计限额(分)
    private String id;
    private String logourl;
    private String productcategory;
    private int transamt;//单笔限额

    public String getAmtdesc() {
        return amtdesc;
    }

    public void setAmtdesc(String amtdesc) {
        this.amtdesc = amtdesc;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankno() {
        return bankno;
    }

    public void setBankno(String bankno) {
        this.bankno = bankno;
    }

    public String getBiztype() {
        return biztype;
    }

    public void setBiztype(String biztype) {
        this.biztype = biztype;
    }

    public int getDateamt() {
        return dateamt;
    }

    public void setDateamt(int dateamt) {
        this.dateamt = dateamt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogourl() {
        return logourl;
    }

    public void setLogourl(String logourl) {
        this.logourl = logourl;
    }

    public String getProductcategory() {
        return productcategory;
    }

    public void setProductcategory(String productcategory) {
        this.productcategory = productcategory;
    }

    public int getTransamt() {
        return transamt;
    }

    public void setTransamt(int transamt) {
        this.transamt = transamt;
    }
}
