package com.zhongdi.miluo.constants;

/**
 * Created by kenn on 2017/9/18.
 */

public class URLConfig {


    /**
     * 主接口-正式地址
     */
//    赵炎
    public static final String BASE_URL = "http://192.168.64.120:8085";
    //翟万鹏
//    public static final String BASE_URL = "http://192.168.64.154:8085";
    /**
     * 测试接口
     */
//    public static final String URL = "http://202.102.101.138:12099/Service.asmx/GetInformation";

    public static final String LOGIN = BASE_URL + "/lead/v1/user/login";
    /**
     * 验证码
     */
    public static final String SEND_MSG = BASE_URL + "/lead/v1/user/sendMessage";
    /**
     * 注册
     */
    public static final String REGISTER = BASE_URL + "/lead/v1/user/register";
    /**
     * 基金类型
     */
    public static final String FUND_TYPE = BASE_URL + "/fund/fundType";
    /**
     * 基金列表
     */
    public static final String FUND_LIST = BASE_URL + "/fund/fundList";
    /**
     * 开户
     */
    public static final String OPNE_ACCOUNT = BASE_URL + "/lead/mobile/v1/openAccount";
}
