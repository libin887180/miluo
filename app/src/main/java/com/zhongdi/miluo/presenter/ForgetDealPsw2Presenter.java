package com.zhongdi.miluo.presenter;

import android.text.TextUtils;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.ForgetDealPsw2View;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class ForgetDealPsw2Presenter extends BasePresenter<ForgetDealPsw2View> {
    public ForgetDealPsw2Presenter(ForgetDealPsw2View view) {
        super.attachView(view);
    }
    public void modifyDealPsw(String  username ,String psw1, String psw2) {
        if (TextUtils.isEmpty(psw1)) {
            view.showToast("请输入确认交易密码");
            return;
        }
        if (TextUtils.isEmpty(psw2)) {
            view.showToast("请输入确认交易密码");
            return;
        }
        if (!TextUtils.equals(psw1,psw2)) {
            view.showToast("两次密码不一致，请重新输入");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("password", psw1);
        map.put("type", "5");//2：修改登录密码 3：重置登录密码 4：修改交易密码 5：重置交易密码
        map.put("username", username);
        Callback.Cancelable post = netRequestUtil.post(URLConfig.MODIFY_DEAL_PSW, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<Object>>() {
                    @Override
                    public void onSuccess(MResponse<Object> response, int requestCode) {
                        view.onSuccess();
                    }

                    @Override
                    public void onFailed(MResponse<Object> response, int requestCode) {
                        if(response.getCode().equals(ErrorCode.LOGIN_TIME_OUT)){
                            view.reLogin();
                        }else{
                            view.showToast(response.getMsg());
                        }
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
