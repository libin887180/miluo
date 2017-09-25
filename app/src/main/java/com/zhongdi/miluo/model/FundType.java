package com.zhongdi.miluo.model;

import java.io.Serializable;

/**
 * Created by kenn on 2017/9/25.
 */

public class FundType  implements Serializable{

    private String Content_id;
    private String description;
    private String id;
    private String dicno;

    public String getContent_id() {
        return Content_id;
    }

    public void setContent_id(String Content_id) {
        this.Content_id = Content_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDicno() {
        return dicno;
    }

    public void setDicno(String dicno) {
        this.dicno = dicno;
    }
}
