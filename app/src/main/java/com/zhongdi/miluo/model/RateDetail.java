package com.zhongdi.miluo.model;

/**
 * Created by libin on 2017/10/11.
 */

public class RateDetail{
        /**
         * id : 53467
         * sellFundId : 2217
         * minsubscribeamt :
         * manageRate :
         * custodyRate :
         * rateType : 00
         * rateTypeName : 前端认购
         * amountDownLimit : 0
         * amountUpLimit : 50
         * rateValue : 1.00%
         * amountDesc : 购买金额<50万
         * discount :
         */

        private String id;
        private String sellFundId;
        private String minsubscribeamt;
        private String manageRate;
        private String custodyRate;
        private String rateType;
        private String rateTypeName;
        private String amountDownLimit;
        private String amountUpLimit;
        private String rateValue;
        private String amountDesc;
        private String discount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSellFundId() {
            return sellFundId;
        }

        public void setSellFundId(String sellFundId) {
            this.sellFundId = sellFundId;
        }

        public String getMinsubscribeamt() {
            return minsubscribeamt;
        }

        public void setMinsubscribeamt(String minsubscribeamt) {
            this.minsubscribeamt = minsubscribeamt;
        }

        public String getManageRate() {
            return manageRate;
        }

        public void setManageRate(String manageRate) {
            this.manageRate = manageRate;
        }

        public String getCustodyRate() {
            return custodyRate;
        }

        public void setCustodyRate(String custodyRate) {
            this.custodyRate = custodyRate;
        }

        public String getRateType() {
            return rateType;
        }

        public void setRateType(String rateType) {
            this.rateType = rateType;
        }

        public String getRateTypeName() {
            return rateTypeName;
        }

        public void setRateTypeName(String rateTypeName) {
            this.rateTypeName = rateTypeName;
        }

        public String getAmountDownLimit() {
            return amountDownLimit;
        }

        public void setAmountDownLimit(String amountDownLimit) {
            this.amountDownLimit = amountDownLimit;
        }

        public String getAmountUpLimit() {
            return amountUpLimit;
        }

        public void setAmountUpLimit(String amountUpLimit) {
            this.amountUpLimit = amountUpLimit;
        }

        public String getRateValue() {
            return rateValue;
        }

        public void setRateValue(String rateValue) {
            this.rateValue = rateValue;
        }

        public String getAmountDesc() {
            return amountDesc;
        }

        public void setAmountDesc(String amountDesc) {
            this.amountDesc = amountDesc;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }
}
