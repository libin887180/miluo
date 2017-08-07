package com.zhongdi.miluo.base;

import com.zhongdi.miluo.net.NetRequestUtil;

/**
 * Created by isfaaghyth on 6/18/17.
 */

public class BasePresenter<V> {
    protected NetRequestUtil netRequestUtil;
    public V view;

    public void attachView(V view) {
        this.view = view;
        netRequestUtil = NetRequestUtil.getInstance();
    }

    public void dettachView() {
        this.view = null;
    }


    protected void stop() {
    }
}
