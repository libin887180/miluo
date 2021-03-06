package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.RegistView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class RegistPresenter extends BasePresenter<RegistView> {
    public RegistPresenter(RegistView view) {
        super.attachView(view);
    }

    public void regist(String userName, String password, String validateseq) {
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", password);
        map.put("validateseq", validateseq);
        map.put("registrationchannels", "1");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.REGISTER, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<Object>>() {
                    @Override
                    public void onSuccess(MResponse<Object> response, int requestCode) {
                        view.onSuccess();
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

    public void sendMessage(String userName) {
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("type", "1");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.SEND_MSG, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<String>>() {
                    @Override
                    public void onSuccess(MResponse<String> response, int requestCode) {

                    }

                    @Override
                    public void onFailed(MResponse<String> response, int requestCode) {
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
