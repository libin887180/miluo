package com.zhongdi.miluo.model;

import java.util.List;

/**
 * Created by libin on 2017/10/16.
 */

public class TradeRecord {


    /**
     * part1 : {"amount":100,"fundid":16419,"fundtype":1,"reqtime":"2017-10-11  10:21","fundcode":"000803","cancelstatus":"1","title":"申购（元）"}
     * part2 : {"currentStep":"1","result":"申请已受理","steps":[{"time":"2017-10-11  10:21","title":"申请已受理"},{"time":"","title":"份额确认开始收益"},{"time":"","title":"查看收益"}]}
     * part3 : [{"key1":"产品信息","key2":"工银研究精选股票(000803)","key3":"000803"},{"key1":"支付方式","key2":"http://admin.leadfund.com.cn/uploadfile/20151117/201511171003361479.png","key3":"工商银行"},{"key1":"交易状态","key2":"申请受理","key3":""},{"key1":"买入金额","key2":100,"key3":""}]
     */

    private Part1Bean part1;
    private Part2Bean part2;
    private List<Part3Bean> part3;

    public Part1Bean getPart1() {
        return part1;
    }

    public void setPart1(Part1Bean part1) {
        this.part1 = part1;
    }

    public Part2Bean getPart2() {
        return part2;
    }

    public void setPart2(Part2Bean part2) {
        this.part2 = part2;
    }

    public List<Part3Bean> getPart3() {
        return part3;
    }

    public void setPart3(List<Part3Bean> part3) {
        this.part3 = part3;
    }

    public static class Part1Bean {
        /**
         * amount : 100.0
         * fundid : 16419
         * fundtype : 1
         * reqtime : 2017-10-11  10:21
         * fundcode : 000803
         * cancelstatus : 1
         * title : 申购（元）
         */

        private double amount;
        private int fundid;
        private int fundtype;
        private String reqtime;
        private String fundcode;
        private String cancelstatus;
        private String title;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getFundid() {
            return fundid;
        }

        public void setFundid(int fundid) {
            this.fundid = fundid;
        }

        public int getFundtype() {
            return fundtype;
        }

        public void setFundtype(int fundtype) {
            this.fundtype = fundtype;
        }

        public String getReqtime() {
            return reqtime;
        }

        public void setReqtime(String reqtime) {
            this.reqtime = reqtime;
        }

        public String getFundcode() {
            return fundcode;
        }

        public void setFundcode(String fundcode) {
            this.fundcode = fundcode;
        }

        public String getCancelstatus() {
            return cancelstatus;
        }

        public void setCancelstatus(String cancelstatus) {
            this.cancelstatus = cancelstatus;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class Part2Bean {
        /**
         * currentStep : 1
         * result : 申请已受理
         * steps : [{"time":"2017-10-11  10:21","title":"申请已受理"},{"time":"","title":"份额确认开始收益"},{"time":"","title":"查看收益"}]
         */

        private String currentStep;
        private String result;
        private List<StepsBean> steps;

        public String getCurrentStep() {
            return currentStep;
        }

        public void setCurrentStep(String currentStep) {
            this.currentStep = currentStep;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public List<StepsBean> getSteps() {
            return steps;
        }

        public void setSteps(List<StepsBean> steps) {
            this.steps = steps;
        }

        public static class StepsBean {
            /**
             * time : 2017-10-11  10:21
             * title : 申请已受理
             */

            private String time;
            private String title;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }

    public static class Part3Bean {
        /**
         * key1 : 产品信息
         * key2 : 工银研究精选股票(000803)
         * key3 : 000803
         */

        private String key1;
        private String key2;
        private String key3;

        public String getKey1() {
            return key1;
        }

        public void setKey1(String key1) {
            this.key1 = key1;
        }

        public String getKey2() {
            return key2;
        }

        public void setKey2(String key2) {
            this.key2 = key2;
        }

        public String getKey3() {
            return key3;
        }

        public void setKey3(String key3) {
            this.key3 = key3;
        }
    }
}
