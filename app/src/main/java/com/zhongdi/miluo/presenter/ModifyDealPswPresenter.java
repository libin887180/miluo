package com.zhongdi.miluo.presenter;

import android.text.TextUtils;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.ModifyDealPswView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class ModifyDealPswPresenter extends BasePresenter<ModifyDealPswView> {
    public ModifyDealPswPresenter(ModifyDealPswView view) {
        super.attachView(view);
    }
    public void modifyPsw(String org_psw, String new_psw1,String new_psw2) {
        if (TextUtils.isEmpty(org_psw)) {
            view.showToast("请输入原始交易密码");
            return;
        }
        if (TextUtils.isEmpty(new_psw1)) {
            view.showToast("请输入交易密码");
            return;
        }
        if (TextUtils.isEmpty(new_psw2)) {
            view.showToast("请输入确认交易密码");
            return;
        }
        if (!TextUtils.equals(new_psw1,new_psw2)) {
            view.showToast("两次新密码不一致，请重新输入");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("oldPassword", org_psw);
        map.put("password", new_psw1);
        map.put("type", "4");//2：修改登录密码 3：重置登录密码 4：修改交易密码 5：重置交易密码
        map.put("username", SpCacheUtil.getInstance().getLoginAccount());
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.MODIFY_DEAL_PSW, map, 101,
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
