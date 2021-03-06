package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.BeforeBuyInfo;
import com.zhongdi.miluo.model.BuyResponse;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.BuyTiyanjinView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class BuyTiyanjinPresenter extends BasePresenter<BuyTiyanjinView> {
    public BuyTiyanjinPresenter(BuyTiyanjinView view) {
        super.attachView(view);
    }

    public void beforeBuyInit(String fundCode) {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("fundCode", fundCode);
        map.put("type", "0");//-1普通 0 体验金 1 新手日日赚 2新手周周赚 3新手月月赚

        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_BUY_BEFORE, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<BeforeBuyInfo>>() {
                    @Override
                    public void onSuccess(MResponse<BeforeBuyInfo> response, int requestCode) {
                        view.onDataSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<BeforeBuyInfo> response, int requestCode) {
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

    public void buyFund(String fundCode, String tradepwd, String transamount) {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("productid", fundCode);
        map.put("tradepwd", tradepwd);
        map.put("transamount", transamount);
        map.put("type", "0");//-1普通 0 体验金 1 新手日日赚 2新手周周赚 3新手月月赚
        map.put("source", IntentConfig.EX_Gold_Apply+"");

        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.BUY_FUND, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<BuyResponse>>() {
                    @Override
                    public void onSuccess(MResponse<BuyResponse> response, int requestCode) {
                        view.onBuySuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<BuyResponse> response, int requestCode) {
                        if(response.getCode().equals(ErrorCode.ERRORTRADEPWD)){
                            view.showToast(response.getMsg());
                        }else if(response.getCode().equals(ErrorCode.LOCKEDTRADEPWD)){
                            view.showPswLocked();
                        }else if(response.getCode().equals(ErrorCode.LOGIN_TIME_OUT)){
                            MyApplication.getInstance().isLogined =false;
                            view.reLogin();
                        }else{
                            view.showToast(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ViseLog.e(e);
                    }

                    @Override
                    public void onFinished() {
                        view.dismissLoadingDialog();
                    }
                });
    }

}
