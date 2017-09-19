package com.zhongdi.miluo.presenter;


import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.Manager;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.QuickLoginView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by isfaaghyth on 6/17/17.
 */

public class QuickLoginPresenter extends BasePresenter<QuickLoginView> {

    public QuickLoginPresenter(QuickLoginView view) {
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

    public void request_post_3(String mobile, String validateseq) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("validateseq", validateseq);
        Callback.Cancelable post = NetRequestUtil.getInstance().post("http://192.168.64.121:8085/lead/mobile/v1/user/pay", map, 101,
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

    public void login(String email, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("username", email);
        map.put("password", password);
        Callback.Cancelable post = netRequestUtil.post("http://192.168.64.121:8085/login", map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<Manager>>>() {
                    @Override
                    public void onSuccess(MResponse<List<Manager>> response, int requestCode) {
                        ViseLog.w(response.getCode());
                        request_post_3("admin", "1123456");
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

}
