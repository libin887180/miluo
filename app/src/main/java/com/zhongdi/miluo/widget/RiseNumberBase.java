package com.zhongdi.miluo.widget;

/**
 * Created by kenn on 2017/8/14.
 */

public interface RiseNumberBase {
    public void start();

    public RiseNumberTextView withNumber(float number);
    public RiseNumberTextView withNumber(double number);

    public RiseNumberTextView withNumber(float number, boolean flag);

    public RiseNumberTextView withNumber(int number);

    public RiseNumberTextView setDuration(long duration);

    public void setOnEnd(RiseNumberTextView.EndListener callback);
}
