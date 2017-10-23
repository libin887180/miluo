package com.zhongdi.miluo.model;

import java.util.List;

/**
 * Created by kenn on 2017/9/25.
 */

public class FundHistoryValueResponse {

    private  int total;
    private List<HistoryValue> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<HistoryValue> getData() {
        return data;
    }

    public void setData(List<HistoryValue> data) {
        this.data = data;
    }
}
