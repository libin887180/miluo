package com.zhongdi.miluo.model;

import java.util.List;

/**
 * Created by libin on 2017/10/13.
 */

public class FundDividendResponse {

    private  int total;
    private List<FundDividend> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<FundDividend> getData() {
        return data;
    }

    public void setData(List<FundDividend> data) {
        this.data = data;
    }
}
