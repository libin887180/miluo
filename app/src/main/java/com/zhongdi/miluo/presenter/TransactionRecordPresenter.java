package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.TradeRecord;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.TransactionRecordView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class TransactionRecordPresenter extends BasePresenter<TransactionRecordView> {
    public TransactionRecordPresenter(TransactionRecordView view) {
        super.attachView(view);
    }

    public void getTransRecord(String tradeid, String tradeType) {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("tradeid", tradeid);
        map.put("type", tradeType);
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.TRANS_RECORD, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<TradeRecord>>() {
                    @Override
                    public void onSuccess(MResponse<TradeRecord> response, int requestCode) {
                        view.OnDataSuccess(response.getBody());

                    }

                    @Override
                    public void onFailed(MResponse<TradeRecord> response, int requestCode) {
                        if (response.getCode().equals(ErrorCode.LOGIN_TIME_OUT)) {
                            MyApplication.getInstance().isLogined =false;
                            view.reLogin();
                        } else {
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

    public void fundWithdraw(String tradeid, String tradepwd, String type) {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("tradeid", tradeid);
        map.put("tradepwd", tradepwd);
        map.put("type", type);
        map.put("source", IntentConfig.Trade_Record_Cancel+"");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_CHEDAN, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<Object>>() {
                    @Override
                    public void onSuccess(MResponse<Object> response, int requestCode) {
                        view.OnChedanSuccess();
                    }

                    @Override
                    public void onFailed(MResponse<Object> response, int requestCode) {
                        if (response.getCode().equals(ErrorCode.ERRORTRADEPWD)) {
                            view.showToast(response.getMsg());
                        } else if (response.getCode().equals(ErrorCode.LOCKEDTRADEPWD)) {
                            view.showPswLocked();
                        } else if (response.getCode().equals(ErrorCode.LOGIN_TIME_OUT)) {
                            MyApplication.getInstance().isLogined =false;
                            view.reLogin();
                        } else {
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
