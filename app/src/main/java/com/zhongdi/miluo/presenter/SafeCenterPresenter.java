package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.SafeCenterView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class SafeCenterPresenter extends BasePresenter<SafeCenterView> {
    public SafeCenterPresenter(SafeCenterView view) {
        super.attachView(view);
    }

    public void loginOut() {

        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.LOGIN_OUT, map, 101,
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
