package com.zhongdi.miluo.cache;

import android.content.Context;

/**
 * @Description: SharedPreferences存储，支持对象加密存储
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2016-12-19 15:12
 */
public class SpCacheUtil {
    private static SpCacheUtil instance;
    private SpCache spCache;

    public SpCacheUtil(Context context) {
        spCache = SpCache.getInstance(context);
    }

    public static SpCacheUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SpCacheUtil(context);
        }
        return instance;
    }

    /**
     * 保存是否是第一次使用
     *
     * @param isFirstUse
     *            true-第一次使用
     */
    public void setIsFirstUse(boolean isFirstUse) {
        spCache.put("IS_FIRST_USE", isFirstUse);
    }

    /**
     * 获取是否是第一次使用
     *
     * @return true-第一次使用
     */
    public boolean isFirstUse() {
        return spCache.get("IS_FIRST_USE", true);
    }


    /**
     * 保存登录账号(用户名)
     *
     * @param userAccount
     */
    public void setLoginAccount(String userAccount) {
        spCache.put("ACCOUNT", userAccount);
    }

    /**
     * 获取登录账号(用户名)
     *
     * @return 如：cgzjobdcs@cgzjobdcs
     */
    public String getLoginAccount() {
        return spCache.get("ACCOUNT", "");
    }
    /**
     * 保存登录密码
     *
     * @param userPwd
     */
    public void setUserPwd(String userPwd) {
        spCache.put("USER_PASSWORD", userPwd);
    }

    /**
     * 获取登录密码
     *
     * @return
     */
    public String getUserPwd() {
        return spCache.get("USER_PASSWORD", "");
    }


}
