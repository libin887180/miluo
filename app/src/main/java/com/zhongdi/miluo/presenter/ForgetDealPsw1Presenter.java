package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.ForgetDealPsw1View;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class ForgetDealPsw1Presenter extends BasePresenter<ForgetDealPsw1View> {
    public ForgetDealPsw1Presenter(ForgetDealPsw1View view) {
        super.attachView(view);
    }

    public void checkUserInfo(String idno, String name) {
        Map<String, String> map = new HashMap<>();
        map.put("idno", idno);
        map.put("name", name);
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.CHECK_ID_NO, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<Object>>() {
                    @Override
                    public void onSuccess(MResponse<Object> response, int requestCode) {
                        view.toNext();
                    }

                    @Override
                    public void onFailed(MResponse<Object> response, int requestCode) {
                        if(response.getCode().equals(ErrorCode.LOGIN_TIME_OUT)){
                            MyApplication.getInstance().isLogined =false;
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
