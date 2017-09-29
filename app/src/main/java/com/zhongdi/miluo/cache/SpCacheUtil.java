package com.zhongdi.miluo.cache;

import android.content.Context;

import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.model.UserInfo;

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

    public static SpCacheUtil getInstance() {
        if (instance == null) {
            instance = new SpCacheUtil(MyApplication.getInstance());
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
    public void saveLoginAccount(String userAccount) {
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
     * 保存登录账号信息
     *
     */
    public void saveUserInfo(UserInfo   userInfo) {
        spCache.put("ACCOUNT", userInfo.getUsername());
        spCache.putInt("USER_LEVEL", userInfo.getLevel());
        spCache.put("USER_STATE", userInfo.getState());
        spCache.put("USER_ID", userInfo.getUid()+"");
        spCache.putInt("FUND_STATE", userInfo.getFundStatus());
    }

    /**
     * 获取用户评测等级
     *
     * @return 如：cgzjobdcs@cgzjobdcs
     */
    public int getUserTestLevel() {
        return spCache.getInt("USER_LEVEL",-1);
    }
    /**
     * 获取用户id
     */
    public String getUserId() {
        return spCache.get("USER_ID","");
    }

    /**
     * 获取用户开户状态
     */
    public int getUserFundState() {
        return spCache.getInt("FUND_STATE",0);
    }
    /**
     * 获取用户状态
     */
    public String getUserState() {
        return spCache.get("USER_STATE","");
    }

    /**
     * 保存真实姓名
     *
     * @param userAccount
     */
    public void saveRealName(String userAccount) {
        spCache.put("REAL_NAME", userAccount);
    }

    /**
     * 获取真实姓名
     *
     * @return 如：cgzjobdcs@cgzjobdcs
     */
    public String getRealName() {
        return spCache.get("REAL_NAME", "李斌斌");
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