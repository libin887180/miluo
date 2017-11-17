package com.zhongdi.miluo.model;

import java.util.List;

/**
 * Created by libin on 2017/11/17.
 */

public class FundValuationResponse {
    List<FundValuation> fundValuation;
    List<MarketYieldData> marketYieldData;

    public List<FundValuation> getFundValuation() {
        return fundValuation;
    }

    public void setFundValuation(List<FundValuation> fundValuation) {
        this.fundValuation = fundValuation;
    }

    public List<MarketYieldData> getMarketYieldData() {
        return marketYieldData;
    }

    public void setMarketYieldData(List<MarketYieldData> marketYieldData) {
        this.marketYieldData = marketYieldData;
    }
}
