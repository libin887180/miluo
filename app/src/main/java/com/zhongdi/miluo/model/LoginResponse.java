package com.zhongdi.miluo.model;

/**
 * Created by kenn on 2017/9/20.
 */

public class LoginResponse {
    private String code;
    private String msg;
    private UserInfo body;

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

    public UserInfo getBody() {
        return body;
    }

    public void setBody(UserInfo body) {
        this.body = body;
    }
}
