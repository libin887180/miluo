package com.zhongdi.miluo.model;

/**
 * Created by kenn on 2017/9/25.
 */

public class Fund {

    private double netvalue;
    private int fundtype;
    private int semesterrate;
    private String fundcode;
    private int weekrate;
    private String fundname;
    private int seasonrate;
    private int yearyld;
    private double dayrate;
    private int monthrate;
    private int yearrate;
    private int id;
    private String valuedate;
    private long add_time;

    public double getNetvalue() {
        return netvalue;
    }

    public void setNetvalue(double netvalue) {
        this.netvalue = netvalue;
    }

    public int getFundtype() {
        return fundtype;
    }

    public void setFundtype(int fundtype) {
        this.fundtype = fundtype;
    }

    public int getSemesterrate() {
        return semesterrate;
    }

    public void setSemesterrate(int semesterrate) {
        this.semesterrate = semesterrate;
    }

    public String getFundcode() {
        return fundcode;
    }

    public void setFundcode(String fundcode) {
        this.fundcode = fundcode;
    }

    public int getWeekrate() {
        return weekrate;
    }

    public void setWeekrate(int weekrate) {
        this.weekrate = weekrate;
    }

    public String getFundname() {
        return fundname;
    }

    public void setFundname(String fundname) {
        this.fundname = fundname;
    }

    public int getSeasonrate() {
        return seasonrate;
    }

    public void setSeasonrate(int seasonrate) {
        this.seasonrate = seasonrate;
    }

    public int getYearyld() {
        return yearyld;
    }

    public void setYearyld(int yearyld) {
        this.yearyld = yearyld;
    }

    public double getDayrate() {
        return dayrate;
    }

    public void setDayrate(double dayrate) {
        this.dayrate = dayrate;
    }

    public int getMonthrate() {
        return monthrate;
    }

    public void setMonthrate(int monthrate) {
        this.monthrate = monthrate;
    }

    public int getYearrate() {
        return yearrate;
    }

    public void setYearrate(int yearrate) {
        this.yearrate = yearrate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValuedate() {
        return valuedate;
    }

    public void setValuedate(String valuedate) {
        this.valuedate = valuedate;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }
}
