package com.zhongdi.miluo.constants;

/**
 * Created by Administrator on 2017/6/22.
 */
public class ErrorCode {

    public static final String SUCCESS = "0";  //成功

    public static final String SYSERROR = "999999"; //系统异常,请联系管理员!

    public static final String SIGNLEGAL = "INSIDE_1001";  //签名不通过

    public static final String SYSTEMERROR = "INSIDE_9999";  //签名不通过

    public static final String ERRCODE = "5000";//错误码

    public static final String NOAUTH = "403";//没有权限

    public static final String SYSERROE = "error";//没有权限

    public static final String EMPTYUSER = "-1";//账号不存在

    public static final String ERRORPWDE = "ERRORPWDE";//密码错误

    public static final String USERLOCKED = "USERLOCKED";//用户被锁定

    public static final String ERRORSENDMESSAGE = "-4";//发送验证码异常

    public static final String ERRORMESSAGE = "-5";//短信验证码错误

    public static final String EXISTUSER = "-6";//该用户名已经注册

    public static final String NOTEXISTUSER = "-7";//用户名不存在

    public static final String ERRORUPPWD = "-8";//修改密码失败

    public static final String NOTEXISTACCOUNT = "-9";//该账号不存在

    public static final String EMPTYSEL = "-10";//模糊查询内容为空

    public static final String EMPTYPWD = "-11";//密码不能为空

    public static final String SIMPLEPWD = "-12";//不能和原密码一致

    public static final String nameoridno = "-13";//身份证号和姓名不符

}