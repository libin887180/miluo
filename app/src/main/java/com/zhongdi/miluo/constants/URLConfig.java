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
    //秦松
//    public static final String BASE_URL = "http://192.168.64.13:8085";
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
     * 验证码校验
     */
    public static final String SEND_MSG_CHECK = BASE_URL + "/lead/v1/user/isvalidateseq";
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
    /**
     * 开户短信发送
     */
    public static final String OPNE_ACCOUNT_SENDCODE = BASE_URL + "/lead/mobile/v1/resendMessage";
    /**
     * 开户确认
     */
    public static final String OPNE_ACCOUNT_CONFIRM = BASE_URL + "/lead/mobile/v1/openAccountConfirm";
    /**
     * 获取银行列表
     */
    public static final String QUERY_BANKS = BASE_URL + "/lead/v1/bankCardInfoQry";
    /**
     * 风险评测题目
     */
    public static final String RISK_TEST_QUESTION = BASE_URL + "/lead/v1/selrisk";
    /**
     * 风险答案提交
     */
    public static final String RISK_SUBMIT= BASE_URL + "/lead/v1/user/riskevaluating";
    /**
     * 修改登录密码
     */
    public static final String MODIFY_LOGIN_PSW= BASE_URL + "/lead/v1/user/modifypassword";
    /**
     * 修改登录密码
     */
    public static final String MODIFY_DEAL_PSW= BASE_URL + "/lead/v1/user/modifytradpassword";
    /**
     * 基金搜索
     */
    public static final String SEARCH_FUND= BASE_URL + "/lead/v1/fundQry";
    /**
     * 热搜
     */
    public static final String HOT_SEARCH= BASE_URL + "/lead/v1/hotSearch";
}
