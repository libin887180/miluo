package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.OpenAccountView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class OpenAccoutPresenter extends BasePresenter<OpenAccountView> {
    public OpenAccoutPresenter(OpenAccountView view) {
        super.attachView(view);
    }

    public void openAccount(Map<String, String> requestMap) {
        view.showLoadingDialog();
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.OPNE_ACCOUNT, requestMap, 101,
                new NetRequestUtil.NetResponseListener<MResponse<String>>() {
                    @Override
                    public void onSuccess(MResponse<String> response, int requestCode) {
//                            view.toOpenSuccess();
                        view.showCodeDialog();
                    }

                    @Override
                    public void onFailed(MResponse<String> response, int requestCode) {
                        view.showToast(response.getMsg());
//                        view.toOpenSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {
                        view.dismissLoadingDialog();
                    }

                });
    }

    public void openAccountConfirm(String smscode) {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("smscode", smscode);
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.OPNE_ACCOUNT_CONFIRM, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<Object>>() {
                    @Override
                    public void onSuccess(MResponse<Object> response, int requestCode) {
                        view.toOpenSuccess();
                    }

                    @Override
                    public void onFailed(MResponse<Object> response, int requestCode) {

//                        if (response.getCode().equals(ErrorCode.ERROR_MSG)) {
//                            view.showCodeError();
//                        } else {
                        view.showToast(response.getMsg());
//                        }

//                        view.showCodeError();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {
                        view.dismissLoadingDialog();
                    }

                });


    }

    public void resendMessage(String phoneNum) {

        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.OPNE_ACCOUNT_SENDCODE, map, 101,
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
