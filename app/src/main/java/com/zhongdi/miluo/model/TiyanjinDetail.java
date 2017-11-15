package com.zhongdi.miluo.model;

/**
 * Created by libin on 2017/10/31.
 */

public class TiyanjinDetail {

    /**
     * activity_status : string
     * amount : string
     * fundcode : string
     * fundname : string
     * id : string
     * profit : string
     * profits : string
     * status : string
     */

    private String activity_status;
    private String amount;
    private String fundcode;
    private String fundname;
    private String id;
    private String profit;
    private String profits;
    private String status;
    private String winprize_id;

    public String getWinprize_id() {
        return winprize_id;
    }

    public void setWinprize_id(String winprize_id) {
        this.winprize_id = winprize_id;
    }

    public String getActivity_status() {
        return activity_status;
    }

    public void setActivity_status(String activity_status) {
        this.activity_status = activity_status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getProfits() {
        return profits;
    }

    public void setProfits(String profits) {
        this.profits = profits;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
