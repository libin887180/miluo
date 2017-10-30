package com.zhongdi.miluo;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance = null;
    public boolean isLogined;
    public boolean netState = true;

    @Override

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        JPushInterface.init(this); // 初始化 JPush
        JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        MyIntentService.start(this);
//        SystemClock.sleep(1000);

/**
 * 初始化common库
 * 参数1:上下文，不能为空
 * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
 * 参数3:Push推送业务的secret
 */

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        //设置是否对日志信息进行加密, 默认false(不加密).
        UMConfigure.setLogEnabled(true);
        UMConfigure.setEncryptEnabled (false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        PlatformConfig.setWeixin("111111", "11111");
        PlatformConfig.setQQZone("3333333", "333333333");
        PlatformConfig.setSinaWeibo("22222", "2222222", "");
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
