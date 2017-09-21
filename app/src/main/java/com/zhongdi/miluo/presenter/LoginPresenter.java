package com.zhongdi.miluo.presenter;


import android.text.TextUtils;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.Manager;
import com.zhongdi.miluo.model.UserInfo;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.util.StringUtil;
import com.zhongdi.miluo.view.LoginView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
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

    public void request_post_3(String mobile, String validateseq) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("validateseq", validateseq);
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.LOGIN, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<Manager>>>() {


                    @Override
                    public void onSuccess(MResponse<List<Manager>> response, int requestCode) {
                        ViseLog.w(response.getCode());
                    }

                    @Override
                    public void onFailed(MResponse<List<Manager>> response, int requestCode) {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    public void login(String userName, String password) {
        if (!isValid(userName, password)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", password);
        Callback.Cancelable post = netRequestUtil.post(URLConfig.LOGIN, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(MResponse<UserInfo> response, int requestCode) {
                        if (TextUtils.equals(response.getCode(), MiluoConfig.SUCCESS)) {
                            ViseLog.w(response.getBody());
                            SpCacheUtil.getInstance().saveLoginAccount(response.getBody().getName());

                        } else {
                            view.showToast(response.getMsg());
                        }
                    }

                    @Override
                    public void onFailed(MResponse<UserInfo> response, int requestCode) {

                    }


                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

}
