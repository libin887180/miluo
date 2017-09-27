package com.zhongdi.miluo.model;

/**
 * Created by kenn on 2017/9/19.
 */

public class UserInfo {

    private int channel;
    private int level;
    private String name;
    private String password;
    private String state;
    private String uid;
    private String username;
    private int failTimes;
    private int fundStatus;

    public int getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(int fundStatus) {
        this.fundStatus = fundStatus;
    }

    public int getFailTimes() {
        return failTimes;
    }

    public void setFailTimes(int failTimes) {
        this.failTimes = failTimes;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
