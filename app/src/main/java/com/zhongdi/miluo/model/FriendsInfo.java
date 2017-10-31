package com.zhongdi.miluo.model;

/**
 * Created by libin on 2017/10/31.
 */

public class FriendsInfo {

    /**
     * amount : string
     * id : string
     * nums : string
     * status : 0
     */

    private String amount;
    private String id;
    private String nums;
    private int status;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
