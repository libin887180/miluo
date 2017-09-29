package com.zhongdi.miluo;

import android.app.Application;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance = null;
    public boolean isLogined;
    @Override

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MyIntentService.start(this);
//        SystemClock.sleep(1000);
    }


    /**
     * Application  单例
     *
     * @return
     */
    public static MyApplication getInstance() {
        return mInstance;
    }


}
