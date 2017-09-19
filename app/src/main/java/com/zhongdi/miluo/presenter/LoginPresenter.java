package com.zhongdi.miluo.presenter;


import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.Manager;
import com.zhongdi.miluo.model.UserInfo;
import com.zhongdi.miluo.net.NetRequestUtil;
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
//        if (SessionManager.checkExist("is_login")) {
//            if (SessionManager.grabBoolean("is_login")) {
//                view.openMain();
//            }
//        }
    }

    /**
     * 账号格式验证
     */
    public boolean isEmailValid(String email) {
        boolean flag = false;
        String[] accountArray = email.split("@");
        if (accountArray.length == 2 && !"".equals(accountArray[0]) && !"".equals
                (accountArray[1])) {
            flag = true;
        }

        return flag;
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
                    public void onFailed(Throwable e) {
                        e.printStackTrace();
                    }
                });

    }

    public void login(String userName, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", password);
        Callback.Cancelable post = netRequestUtil.post(URLConfig.LOGIN ,map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(MResponse<UserInfo> response, int requestCode) {
                        ViseLog.w(response.getBody());
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

}
