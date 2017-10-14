package com.zhongdi.miluo.model;

/**
 * Created by kenn on 2017/10/14.
 */

public class SellResponse {


    /**
     * bankInfo : {"bankname":"工商银行","card":"尾号6212","logourl":"http://admin.leadfund.com.cn/uploadfile/20151117/201511171003361479.png"}
     * fund : {"fundtype":"股票型","avaliableshare":"399103.85","fundcode":"000803","fundname":"工银研究精选股票","minredemptionvol":10}
     */

    private BankInfo bankInfo;
    private Fund fund;

    public BankInfo getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(BankInfo bankInfo) {
        this.bankInfo = bankInfo;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    public static class BankInfo {
        /**
         * bankname : 工商银行
         * card : 尾号6212
         * logourl : http://admin.leadfund.com.cn/uploadfile/20151117/201511171003361479.png
         */

        private String bankname;
        private String card;
        private String logourl;

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getLogourl() {
            return logourl;
        }

        public void setLogourl(String logourl) {
            this.logourl = logourl;
        }
    }

    public static class Fund {
        /**
         * fundtype : 股票型
         * avaliableshare : 399103.85
         * fundcode : 000803
         * fundname : 工银研究精选股票
         * minredemptionvol : 10.0
         */

        private String fundtype;
        private String avaliableshare;
        private String fundcode;
        private String fundname;
        private double minredemptionvol;
        private String preredeemacctdate;

        public String getPreredeemacctdate() {
            return preredeemacctdate;
        }

        public void setPreredeemacctdate(String preredeemacctdate) {
            this.preredeemacctdate = preredeemacctdate;
        }

        public String getFundtype() {
            return fundtype;
        }

        public void setFundtype(String fundtype) {
            this.fundtype = fundtype;
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

        public double getMinredemptionvol() {
            return minredemptionvol;
        }

        public void setMinredemptionvol(double minredemptionvol) {
            this.minredemptionvol = minredemptionvol;
        }
    }
}
