package com.zhongdi.miluo.presenter;

import android.text.TextUtils;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.util.StringUtil;
import com.zhongdi.miluo.view.SendCodeView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class SendCoderesenter extends BasePresenter<SendCodeView> {
    public SendCoderesenter(SendCodeView view) {
        super.attachView(view);
    }

    public void sendMessage(String tel,String type) {
        if (!StringUtil.isPhoneNum(tel)) {
            view.showToast("请输入正确格式的手机号");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("type", type); //(0：单独注册 1:免注册登录 2：修改登录密码 3：重置登录密码 4：修改交易密码 5：重置交易密码）
        Callback.Cancelable post = netRequestUtil.post(URLConfig.SEND_MSG, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<Object>>() {
                    @Override
                    public void onSuccess(MResponse<Object> response, int requestCode) {
                        if (response.getCode().equals(ErrorCode.SUCCESS))
                            view.showToast("验证码发送成功");
                    }

                    @Override
                    public void onFailed(MResponse<Object> response, int requestCode) {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }

                });
    }

    public void checkCode(String tel, String code,String  type) {
        if (!StringUtil.isPhoneNum(tel)) {
            view.showToast("请输入正确格式的手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            view.showToast("请输入短信验证码");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("validateseq", code);//默认写1
        map.put("type", type);//(0：单独注册 1:免注册登录 2：修改登录密码 3：重置登录密码 4：修改交易密码 5：重置交易密码）
        Callback.Cancelable post = netRequestUtil.post(URLConfig.SEND_MSG_CHECK, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<Object>>() {
                    @Override
                    public void onSuccess(MResponse<Object> response, int requestCode) {
                        view.toNext();
                    }

                    @Override
                    public void onFailed(MResponse<Object> response, int requestCode) {
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


}
