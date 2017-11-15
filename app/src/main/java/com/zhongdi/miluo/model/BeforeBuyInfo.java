package com.zhongdi.miluo.model;

import java.util.List;

/**
 * Created by libin on 2017/10/13.
 */

public class BeforeBuyInfo {


    /**
     * bankInfo : {"amtdesc":"单笔限999万/单日累计999万元","bankname":"工商银行","logourl":"http://admin.leadfund.com.cn/uploadfile/20151117/201511171003361479.png"}
     * fees : [{"ratevalue":"1000","discount":"1","amountdownlimit":500},{"ratevalue":"0.005","discount":"1","amountdownlimit":200},{"ratevalue":"0.008","discount":"1","amountdownlimit":50},{"ratevalue":"0.012","discount":"1","amountdownlimit":0}]
     * fund : {"fundtype":"混合型","fundcode":"163406","fundname":"兴全合润分级(163406)","minsubscribeamt":"1000.000000元起"}
     */

    private BankInfoBean bankInfo;
    private FundBean fund;
    private List<FeesBean> fees;
    private List<NewLevelBean> newLevels;

    public BankInfoBean getBankInfo() {
        return bankInfo;
    }

    public List<NewLevelBean> getNewLevels() {
        return newLevels;
    }

    public void setNewLevels(List<NewLevelBean> newLevels) {
        this.newLevels = newLevels;
    }

    public void setBankInfo(BankInfoBean bankInfo) {
        this.bankInfo = bankInfo;
    }

    public FundBean getFund() {
        return fund;
    }

    public void setFund(FundBean fund) {
        this.fund = fund;
    }

    public List<FeesBean> getFees() {
        return fees;
    }

    public void setFees(List<FeesBean> fees) {
        this.fees = fees;
    }

    public static class BankInfoBean {
        /**
         * amtdesc : 单笔限999万/单日累计999万元
         * bankname : 工商银行
         * logourl : http://admin.leadfund.com.cn/uploadfile/20151117/201511171003361479.png
         */

        private String amtdesc;
        private String bankname;
        private String logourl;

        public String getAmtdesc() {
            return amtdesc;
        }

        public void setAmtdesc(String amtdesc) {
            this.amtdesc = amtdesc;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getLogourl() {
            return logourl;
        }

        public void setLogourl(String logourl) {
            this.logourl = logourl;
        }
    }

    public static class FundBean {
        /**
         * fundtype : 混合型
         * fundcode : 163406
         * fundname : 兴全合润分级(163406)
         * minsubscribeamt : 1000.000000元起
         */

        private String fundtype;
        private String fundcode;
        private String fundname;
        private String minsubscribeamt;
        private int risklevel;

        public int getRisklevel() {
            return risklevel;
        }

        public void setRisklevel(int risklevel) {
            this.risklevel = risklevel;
        }

        public String getFundtype() {
            return fundtype;
        }

        public void setFundtype(String fundtype) {
            this.fundtype = fundtype;
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

        public String getMinsubscribeamt() {
            return minsubscribeamt;
        }

        public void setMinsubscribeamt(String minsubscribeamt) {
            this.minsubscribeamt = minsubscribeamt;
        }
    }

    public static class FeesBean {
        /**
         * ratevalue : 1000
         * discount : 1
         * amountdownlimit : 500.0
         */

        private String ratevalue;
        private String discount;
        private double amountdownlimit;

        public String getRatevalue() {
            return ratevalue;
        }

        public void setRatevalue(String ratevalue) {
            this.ratevalue = ratevalue;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public double getAmountdownlimit() {
            return amountdownlimit;
        }

        public void setAmountdownlimit(double amountdownlimit) {
            this.amountdownlimit = amountdownlimit;
        }
    }
    public static class NewLevelBean {

        private String item;
        private String level;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}
