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
    public boolean assetVisable = true;
    public boolean hasNewMsg;
    public boolean netState = true;
    public boolean islogignShow;
    @Override

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        JPushInterface.init(this); // 初始化 JPush
        JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
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
        UMConfigure.setLogEnabled(false);
        UMConfigure.setEncryptEnabled (false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        PlatformConfig.setWeixin("wx1e262f3b61a0b995", "09da9bf44c1f3ec5d0ded94e2de2c53a");
        PlatformConfig.setSinaWeibo("1690564364", "718b3f56251326fcdc03fe44d31a380d", "https://sns.whalecloud.com/sina2/callback");
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
