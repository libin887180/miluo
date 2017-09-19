package com.zhongdi.miluo.model;

/**
 * Created by libin on 2017/8/1.
 */

public class MResponse<T> {
    private String code;
    private String msg;
    private T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
