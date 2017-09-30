package com.zhongdi.miluo.model;

import java.util.List;

/**
 * Created by kenn on 2017/9/25.
 */

public class OptionalFundResponse {

    private  int total;
    private List<OptionalFund> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OptionalFund> getData() {
        return data;
    }

    public void setData(List<OptionalFund> data) {
        this.data = data;
    }
}
