package com.zhongdi.miluo.model;

import java.util.List;

/**
 * Created by libin on 2017/10/11.
 */

public class RateResponse {


    /**
     * custodyRate : 管理费用
     * RateTypeDetail: 基金类别 列表 包含 前端申购  后端申购  前端赎回 后端赎回等
     * * manageRate : 托管费用
     */

    private String custodyRate;
    private String manageRate;
    private List<RateTypeDetail> list;

    public String getCustodyRate() {
        return custodyRate;
    }

    public void setCustodyRate(String custodyRate) {
        this.custodyRate = custodyRate;
    }

    public String getManageRate() {
        return manageRate;
    }

    public void setManageRate(String manageRate) {
        this.manageRate = manageRate;
    }

    public List<RateTypeDetail> getList() {
        return list;
    }

    public void setList(List<RateTypeDetail> list) {
        this.list = list;
    }


}
