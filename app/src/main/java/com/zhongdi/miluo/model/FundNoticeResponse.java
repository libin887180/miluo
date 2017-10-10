package com.zhongdi.miluo.model;

import java.util.List;

/**
 * Created by kenn on 2017/9/25.
 */

public class FundNoticeResponse {

    private  int total;
    private List<FundNotice> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<FundNotice> getData() {
        return data;
    }

    public void setData(List<FundNotice> data) {
        this.data = data;
    }
}
