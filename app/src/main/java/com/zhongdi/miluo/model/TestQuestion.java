package com.zhongdi.miluo.model;

import java.io.Serializable;

/**
 * Created by libin on 2017/9/13.
 */

public class TestQuestion  implements Serializable{
    private  String question;
    private  String selectionA;
    private  String selectionB;
    private  String selectionC;
    private  String selectionD;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSelectionA() {
        return selectionA;
    }

    public void setSelectionA(String selectionA) {
        this.selectionA = selectionA;
    }

    public String getSelectionB() {
        return selectionB;
    }

    public void setSelectionB(String selectionB) {
        this.selectionB = selectionB;
    }

    public String getSelectionC() {
        return selectionC;
    }

    public void setSelectionC(String selectionC) {
        this.selectionC = selectionC;
    }

    public String getSelectionD() {
        return selectionD;
    }

    public void setSelectionD(String selectionD) {
        this.selectionD = selectionD;
    }
}
