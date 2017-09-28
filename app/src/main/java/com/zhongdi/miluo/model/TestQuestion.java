package com.zhongdi.miluo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by libin on 2017/9/13.
 */

public class TestQuestion  implements Serializable{


    private String title;
    private List<String> value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
