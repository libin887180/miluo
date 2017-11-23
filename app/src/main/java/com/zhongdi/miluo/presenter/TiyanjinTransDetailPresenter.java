package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.FriendsInfo;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.TiyanjinDetail;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.TiyanjinTransDetailView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class TiyanjinTransDetailPresenter extends BasePresenter<TiyanjinTransDetailView> {
    public TiyanjinTransDetailPresenter(TiyanjinTransDetailView view) {
        super.attachView(view);
    }

    public void getTiYanjinDetail() {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.TIYANJIN_TRANS_DETAIL, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<TiyanjinDetail>>() {
                    @Override
                    public void onSuccess(MResponse<TiyanjinDetail> response, int requestCode) {
                        view.OnDataSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<TiyanjinDetail> response, int requestCode) {
                        ViseLog.e("请求失败");

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
                        view.dismissLoadingDialog();
                    }
                });
    }
    public void getFriendsNum() {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FRIENDS_NUM, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<FriendsInfo>>() {
                    @Override
                    public void onSuccess(MResponse<FriendsInfo> response, int requestCode) {
                        view.OnFriendsSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<FriendsInfo> response, int requestCode) {
                        ViseLog.e("请求失败");

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
                        view.dismissLoadingDialog();
                    }
                });
    }


}
