package com.zhongdi.miluo.presenter;


import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.UserInfo;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.util.MiLuoUtil;
import com.zhongdi.miluo.util.StringUtil;
import com.zhongdi.miluo.view.TiyanjinQuickLoginView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by isfaaghyth on 6/17/17.
 */

public class TiyanjinQuickLoginPresenter extends BasePresenter<TiyanjinQuickLoginView> {

    public TiyanjinQuickLoginPresenter(TiyanjinQuickLoginView view) {
        super.attachView(view);
    }

    public void sendMessage(String userName) {

        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("type", "1");//验证码渠道
        Callback.Cancelable post = netRequestUtil.post(URLConfig.SEND_MSG, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<String>>() {
                    @Override
                    public void onSuccess(MResponse<String> response, int requestCode) {
                        view.showToast("验证码发送成功");
                    }

                    @Override
                    public void onFailed(MResponse<String> response, int requestCode) {
                        view.showToast(response.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }

                });
    }

    /**
     * 账号格式验证
     */
    private boolean isEmailValid(String email) {
        boolean flag = false;
        String[] accountArray = email.split("@");
        System.out.println("accountArray.length=" + accountArray.length);
        if (accountArray.length == 2 && !"".equals(accountArray[0]) && !"".equals
                (accountArray[1])) {
            flag = true;
        }

        return flag;
    }

    public void login(String userName, String code, int source) {
        if (!StringUtil.isPhoneNum(userName)) {
            view.showToast("请输入正确格式的手机号");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("channel", "0");
        map.put("validateseq", code);
        map.put("source", source + "");
        map.put("type", "1");//登陆方式 0：正常登陆，1 快速登录
        Callback.Cancelable post = netRequestUtil.post(URLConfig.LOGIN, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(MResponse<UserInfo> response, int requestCode) {
                        ViseLog.w(response.getBody());
                        MiLuoUtil.saveUserInfo(response.getBody());
                        view.loginSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<UserInfo> response, int requestCode) {
                        view.showToast(response.getMsg());
                        ViseLog.e("请求失败");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }
}
