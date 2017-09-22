package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
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

    public void sendMessage(String tel) {
        if (!StringUtil.isPhoneNum(tel)) {
            view.showToast("请输入正确格式的手机号");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("type", "1");
        Callback.Cancelable post = netRequestUtil.post(URLConfig.SEND_MSG, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<String>>() {
                    @Override
                    public void onSuccess(MResponse<String> response, int requestCode) {
                        view.toNext();
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
