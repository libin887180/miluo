package com.zhongdi.miluo.presenter;


import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.UserInfo;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.util.MiLuoUtil;
import com.zhongdi.miluo.util.StringUtil;
import com.zhongdi.miluo.view.LoginView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by isfaaghyth on 6/17/17.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter(LoginView view) {
        super.attachView(view);
    }

    public void isLogin() {


    }

    /**
     * 账号格式验证
     */
    public boolean isValid(String userName, String password) {

        if (!StringUtil.isPhoneNum(userName)) {
            view.showToast("请输入正确格式的手机号");
            return false;
        }

        if (StringUtil.getStringLength(password) < 6) {
            view.showToast("请输入大于6位的密码");
            return false;
        }
        return true;
    }


    public void login(String userName, String password, int source) {
        if (!isValid(userName, password)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", password);
        map.put("channel", "0");
        map.put("source", source + "");
        map.put("type", "0");//登陆方式 0：正常登陆，1 快速登录
        Callback.Cancelable post = netRequestUtil.post(URLConfig.LOGIN, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(MResponse<UserInfo> response, int requestCode) {
                        ViseLog.w(response.getBody());
                       MiLuoUtil.saveUserInfo(response.getBody());
                        view.loginSuccess();
                    }

                    @Override
                    public void onFailed(MResponse<UserInfo> response, int requestCode) {
                        ViseLog.e("请求失败");
                        view.showToast(response.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

}
