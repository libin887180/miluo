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
    //测试环境
//    public static final String BASE_URL = "http://192.168.151.13:8085";


//    public static final String HTML5 = "http://192.168.64.212:8371";
////    外网测试
//    public static final String BASE_URL = "http://218.94.82.38:8085";
    //测试H5
    public static final String HTML5 = "http://192.168.151.13:8371";

//    //正式测试
//    public static final String BASE_URL = "http://47.96.177.50:8085";
//    //正式H5地址
//    public static final String HTML5 = "http://47.96.177.50:8371";

    /**
     * 测试接口
     */
//    public static final String URL = "http://202.102.101.138:12099/Service.asmx/GetInformation";
    /**
     * 登录
     */
    public static final String LOGIN = BASE_URL + "/lead/v1/login";
//    public static final St                                                                                       ring LOGIN = BASE_URL + "/lead/v1/user/login";
    /**
     * 验证码(0：单独注册 1:免注册登录 3：重置登录密码 5：重置交易密码)
     */
    public static final String SEND_MSG = BASE_URL + "/lead/v1/sendMessage";
    /**
     * 验证码校验(1:注册 3：重置登录密码 5：重置交易密码)
     */
    public static final String SEND_MSG_CHECK = BASE_URL + "/lead/v1/isValidateseq";
    /**
     * 验证码(2：修改登录密码 4：修改交易密码)
     */
    public static final String SEND_MODIFY_MSG = BASE_URL + "/lead/v1/user/shiroSendMessage";
    /**
     * 验证码校验(2：修改登录密码 4：修改交易密码)
     */
    public static final String SEND_MODIFY_MSG_CHECK = BASE_URL + "/lead/v1/user/updateIsValidateseq";

    /**
     * 注册
     */
    public static final String REGISTER = BASE_URL + "/lead/v1/register";
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
    public static final String RISK_TEST_QUESTION = BASE_URL + "/lead/v1/user/selrisk";
    /**
     * 风险答案提交
     */
    public static final String RISK_SUBMIT = BASE_URL + "/lead/v1/user/riskevaluating";
    /**
     * 修改登录密码
     */
    public static final String MODIFY_LOGIN_PSW = BASE_URL + "/lead/v1/user/modifyPassword";
    /**
     * 重置登录密码
     */
    public static final String RESET_LOGIN_PSW = BASE_URL + "/lead/v1/resetPassword";
    /**
     * 修改交易密码
     */
    public static final String MODIFY_DEAL_PSW = BASE_URL + "/lead/v1/user/resetTradPassword";
    /**
     * 退出登录
     */
    public static final String LOGIN_OUT = BASE_URL + "/lead/v1/user/logout";
    /**
     * 验证身份证号和姓名
     */
    public static final String CHECK_ID_NO = BASE_URL + "/lead/v1/user/isidno";
    /**
     * 基金搜索
     */
    public static final String SEARCH_FUND = BASE_URL + "/lead/v1/fundQry";
    /**
     * 热搜
     */
    public static final String HOT_SEARCH = BASE_URL + "/lead/v1/hotSearch";
    /**
     * 基金消息
     */
    public static final String FUND_NEWS = BASE_URL + "/lead/v1/user/news";
    /**
     * 自选基金列表
     */
    public static final String OPTIONAL_FUND = BASE_URL + "/fund/optionalFund";
    /**
     * 基金详情
     */
    public static final String FUND_DETAIL = BASE_URL + "/fund/FundDetail";
    /**
     * 基金经理
     */
    public static final String FUND_MANAGER = BASE_URL + "/fund/fundManager";
    /**
     * 基金详情页公告
     */
    public static final String FUND_DETAIL_NOTICE = BASE_URL + "/fund/mainPageAnnouncement";
    /**
     * 基金历史净值
     */
    public static final String FUND_HISTORY_VALUE = BASE_URL + "/fund/historyOffundValue";
    /**
     * 基金公告列表
     */
    public static final String FUND_NOTICE_LIST = BASE_URL + "/fund/fundAnnouncement";
    /**
     * 基金概况
     */
    public static final String FUND_SURVEY = BASE_URL + "/fund/fundSurvey";
    /**
     * 基金重仓股
     */
    public static final String FUND_MAIN_HOLD = BASE_URL + "/fund/listOfZhongCangGu";
    /**
     * 基金资产占比
     */
    public static final String FUND_ZHANBI = BASE_URL + "/fund/assetAllocation";
    /**
     * 基金分红列表
     */
    public static final String FUND_DIVIDEND = BASE_URL + "/fund/fundDividend";
    /**
     * 基金添加自选
     */
    public static final String FUND_COLLECT = BASE_URL + "/fund/addOptionalFund";
    /**
     * 基金删除自选
     */
    public static final String FUND_DIS_COLLECT = BASE_URL + "/fund/delOptionalFund";
    /**
     * 基金费率
     */
    public static final String FUND_RATE = BASE_URL + "/fund/fundRate";
    /**
     * 我的资产
     */
    public static final String MY_PROPERTY = BASE_URL + "/lead/v1/user/myProperty";
    /**
     * 我的资产列表
     */
    public static final String MY_PROPERTY_LIST = BASE_URL + "/lead/v1/user/myPropertyList";
    /**
     * 交易记录
     */
    public static final String TRADE_RECORD_LIST = BASE_URL + "/lead/v1/user/tradeRecordList";
    /**
     * 交易记录曲线
     */
    public static final String NET_VALUE_LINE = BASE_URL + "/lead/v1/user/tradeDetailNetValue";
    /**
     * 交易详情
     */
    public static final String TRADE_DETAIL = BASE_URL + "/lead/v1/user/myPropertyDetail";
    /**
     * 购买初始化
     */
    public static final String FUND_BUY_BEFORE = BASE_URL + "/lead/v1/user/beforeBuy";
    /**
     * 购买基金
     */
    public static final String BUY_FUND = BASE_URL + "/lead/v1/user/fundBuy";
    /**
     * 基金资讯列表
     */
    public static final String FUND_ESSAY = BASE_URL + "/lead/v1/fundEssay";
    /**
     * 基金资讯阅读状态
     */
    public static final String UPSTATUS = BASE_URL + "/lead/v1/upToStatus";
    /**
     * 赎回初始化
     */
    public static final String FUND_SELL_BEFORE = BASE_URL + "/lead/v1/user/beforeRedeem";
    /**
     * 赎回初始化
     */
    public static final String SELL_FUND = BASE_URL + "/lead/v1/user/fundRedeem";
    /**
     * 交易记录
     */
    public static final String TRANS_RECORD = BASE_URL + "/lead/v1/user/tradeRecord";
    /**
     * 交易撤单
     */
    public static final String FUND_CHEDAN = BASE_URL + "/lead/v1/user/fundWithdraw";
    /**
     * 修改分红方式
     */
    public static final String MODIFY_BONUS = BASE_URL + "/lead/v1/user/modifiyBonus";
    /**
     * 我的银行卡
     */
    public static final String MY_BANK_CARDS = BASE_URL + "/lead/v1/user/myBankCards";
    /**
     * 估值曲线
     */
    public static final String FUND_VAL = BASE_URL + "/fund/fundVal";
    /**
     * 收益曲线
     */
    public static final String FUND_VALUATION = BASE_URL + "/fund/fundValuation";
    /**
     * 我的奖品
     */
    public static final String MY_PRIZE = BASE_URL + "/activity/v1/findMyWinprize";
    /**
     * 奖品兑换
     */
    public static final String EX_CHANGE = BASE_URL + "/activity/v1/reChargeBill";
    /**
     * 体验金交易详情
     */
    public static final String TIYANJIN_TRANS_DETAIL = BASE_URL + "/activity/v1/feeDetaileShow";
    /**
     * 获取邀请好友个数和收益
     */
    public static final String FRIENDS_NUM = BASE_URL + "/activity/v1/invitingFriends";
    /**
     * 基金首页资讯
     */
    public static final String AUTO_NEWS = BASE_URL + "/lead/v1/fundAutoNews";
    /**
     * 基金首页新手标
     */
    public static final String NEW_COMMER = BASE_URL + "/juniorActivity/mainJuniorActivity";
    /**
     * 基金首页活动
     */
    public static final String HOME_ACTIVE = BASE_URL + "/mainActivity/selActivity";
    /**
     * 基金投资热点
     */
    public static final String HOT_SPOTS = BASE_URL + "/chooseFund/selHotSpots";
    /**
     * 首页 特色基金，获奖基金，米罗懂你
     */
    public static final String HOME_FUND = BASE_URL + "/chooseFund/selChooseFundList";
    /**
     * 体验金介绍页
     */
    public static final String TIYANJIN = HTML5 + "/lead/v1/experience/getFeeShow?type=1&username=";
    /**
     * 新手
     */
    public static final String JEUNIOR = HTML5 + "/fund/selJuniorActivity";
    /**
     * 小白学基
     */
    public static final String STUDY = HTML5 + "/fund/fundLearningType";
    /**
     * 米罗盘
     */
    public static final String MILUO_PAN = HTML5 + "/fund/fundLearningType";
    /**
     * 特色基金
     */
    public static final String SPECIAL_FUND = HTML5 + "/fund/selCharacteristicList?";
    /**
     * 公告和消息详情
     */
    public static final String NEWS_DETAIL = HTML5 + "/lead/v1/experience/newsDetail?id=";
    /**
     * 晨星奖
     */
    public static final String CHEN_XING = HTML5 + "/fund/selChooseFundList?";
    /**
     * 投资热点详情
     */
    public static final String HOT_SPOT_DETAIL = HTML5 + "/fund/selHotSpotsList?";
    /**
     * 协议
     */
    public static final String PROTOCOL = HTML5 + "/protocol";
    /**
     * 体验金小知识
     */
    public static final String KNOWLEDGE = HTML5 + "/experience_knowledge";
    /**
     * H5登录
     */
    public static final String H5_REGISTER = HTML5 + "/lead/v1/register/useRegister";
    /**
     * 检测升级
     */
    public static final String UPDATE = BASE_URL + "/lead/v1/sysUpgrade";


}
