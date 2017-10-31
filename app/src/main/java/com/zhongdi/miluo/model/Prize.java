package com.zhongdi.miluo.model;

import java.io.Serializable;

/**
 * Created by libin on 2017/10/31.
 */

public class Prize implements Serializable{


    /**
     * add_time : string
     * amount : string
     * id : string
     * invalid_time : string
     * status : string
     * title : string
     * type : string
     */

    private String add_time;
    private String amount;
    private String id;
    private String invalid_time;
    private String status;
    private String title;
    private String type;

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

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

    public String getInvalid_time() {
        return invalid_time;
    }

    public void setInvalid_time(String invalid_time) {
        this.invalid_time = invalid_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
