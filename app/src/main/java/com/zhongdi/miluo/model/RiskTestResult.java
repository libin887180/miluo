package com.zhongdi.miluo.model;

import java.io.Serializable;

/**
 * Created by kenn on 2017/9/28.
 */

public class RiskTestResult implements Serializable {


    private int risklevel;
    private String risklevelname;


    public int getRisklevel() {
        return risklevel;
    }

    public void setRisklevel(int risklevel) {
        this.risklevel = risklevel;
    }

    public String getRisklevelname() {
        return risklevelname;
    }

    public void setRisklevelname(String risklevelname) {
        this.risklevelname = risklevelname;
    }

}
