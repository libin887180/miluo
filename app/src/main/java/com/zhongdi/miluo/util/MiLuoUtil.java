package com.zhongdi.miluo.util;

import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.model.UserInfo;

/**
 * Created by kenn on 2017/9/29.
 */

public class MiLuoUtil {
    public static void saveUserInfo( UserInfo userinfo) {
        SpCacheUtil.getInstance().saveUserInfo(userinfo);
        MyApplication.getInstance().isLogined=true;
    }
}
