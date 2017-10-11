package com.zhongdi.miluo.model;

/**
 * Created by libin on 2017/10/11.
 */

public class FundDividend {

    /**
     * dividendDate : string
     * everyDividend : string
     * exdividendDate : string
     * id : string
     * registerDate : string
     * sellFundId : string
     */

    private String dividendDate;
    private String everyDividend;
    private String exdividendDate;
    private String id;
    private String registerDate;
    private String sellFundId;

    public String getDividendDate() {
        return dividendDate;
    }

    public void setDividendDate(String dividendDate) {
        this.dividendDate = dividendDate;
    }

    public String getEveryDividend() {
        return everyDividend;
    }

    public void setEveryDividend(String everyDividend) {
        this.everyDividend = everyDividend;
    }

    public String getExdividendDate() {
        return exdividendDate;
    }

    public void setExdividendDate(String exdividendDate) {
        this.exdividendDate = exdividendDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getSellFundId() {
        return sellFundId;
    }

    public void setSellFundId(String sellFundId) {
        this.sellFundId = sellFundId;
    }
}
