package com.zhongdi.miluo;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.x;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AutoLayoutConifg.getInstance().useDeviceSize();
        initLog();
        initNet();
    }

    private void initLog() {
//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(0)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
////                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
//                .tag("KENN")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .build();
//        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Logger.init("KENN")
                .methodCount(2) // 方法栈打印的个数，默认是 2
//                .hideThreadInfo() // // 隐藏线程信息，默认显示
                .methodOffset(2) // 设置调用堆栈的函数偏移值，默认是 0
                .logLevel(LogLevel.FULL);
//                .logAdapter(new AndroidLogAdapter()); // 自定义一个打印适配器

    }

    /**
     * Application  单例
     * @return
     */
    public static MyApplication getInstance() {
        return mInstance;
    }

    private void initNet() {
        x.Ext.init(this);

    }
}
