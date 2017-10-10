package com.zhongdi.miluo.model;

import java.io.Serializable;

/**
 * Created by kenn on 2017/10/9.
 */

public class FundManagerInfo implements Serializable{
    private String endDate;
    private String id;
    private String indiImgUrl;
    private String managerName;
    private String resume;
    private String rqhb;
    private String sellFundId;
    private String startDate;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndiImgUrl() {
        return indiImgUrl;
    }

    public void setIndiImgUrl(String indiImgUrl) {
        this.indiImgUrl = indiImgUrl;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getRqhb() {
        return rqhb;
    }

    public void setRqhb(String rqhb) {
        this.rqhb = rqhb;
    }

    public String getSellFundId() {
        return sellFundId;
    }

    public void setSellFundId(String sellFundId) {
        this.sellFundId = sellFundId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
