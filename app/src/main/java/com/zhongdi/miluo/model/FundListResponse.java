package com.zhongdi.miluo.model;

import java.util.List;

/**
 * Created by kenn on 2017/9/25.
 */

public class FundListResponse {

    private  int total;
    private List<Fund> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Fund> getData() {
        return data;
    }

    public void setData(List<Fund> data) {
        this.data = data;
    }
}
