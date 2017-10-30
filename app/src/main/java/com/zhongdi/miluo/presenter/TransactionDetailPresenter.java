package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.DealRecord;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.PropertyDetail;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.TransactionDetailView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class TransactionDetailPresenter extends BasePresenter<TransactionDetailView> {
    public TransactionDetailPresenter(TransactionDetailView view) {
        super.attachView(view);
    }

    public void getPropertyDetail(String fundcode) {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("fundcode", fundcode);
        Callback.Cancelable post = netRequestUtil.post(URLConfig.TRADE_DETAIL, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<PropertyDetail>>() {
                    @Override
                    public void onSuccess(MResponse<PropertyDetail> response, int requestCode) {
                        view.onPropertySuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<PropertyDetail> response, int requestCode) {
                        ViseLog.e("请求失败");
                        view.showToast(response.getMsg());
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


    public void modifyBonus(String bonusmethod, String productid, String tradepwd) {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("bonusmethod", bonusmethod);//红利再投1现金分红
        map.put("productid", productid);
        map.put("tradepwd", tradepwd);
        Callback.Cancelable post = netRequestUtil.post(URLConfig.MODIFY_BONUS, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<Object>>() {
                    @Override
                    public void onSuccess(MResponse<Object> response, int requestCode) {
                        view.modifyBonusSuccess();
                    }

                    @Override
                    public void onFailed(MResponse<Object> response, int requestCode) {
                        if(response.getCode().equals(ErrorCode.ERRORTRADEPWD)){
                            view.showToast(response.getMsg());
                        }else if(response.getCode().equals(ErrorCode.LOCKEDTRADEPWD)){
                            view.showPswLocked();
                        }
                        view.modifyBonusFailed();
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

    /**
     * 获取交易记录
     *
     * @param pageIndex 页数
     * @param pageSize  每页条数
     */
    public void getTradRecords(final int pageIndex, int pageSize, String fundcode) {
        Map<String, String> map = new HashMap<>();
        map.put("pageIndex", pageIndex + "");
        map.put("pageSize", pageSize + "");
        map.put("fundcode", fundcode);//基金代码
        Callback.Cancelable post = netRequestUtil.post(URLConfig.TRADE_RECORD_LIST, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<DealRecord>>>() {
                    @Override
                    public void onSuccess(MResponse<List<DealRecord>> response, int requestCode) {
                        view.onTradRecordsSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<List<DealRecord>> response, int requestCode) {
                        ViseLog.e("请求失败");
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
    /**
     * 获取交易曲线
     *
     */
    public void getLines(String fundcode) {
        Map<String, String> map = new HashMap<>();
        map.put("fundcode", fundcode);//基金代码
        Callback.Cancelable post = netRequestUtil.post(URLConfig.NET_VALUE_LINE, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<Object>>() {
                    @Override
                    public void onSuccess(MResponse<Object> response, int requestCode) {
//                        view.onTradRecordsSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<Object> response, int requestCode) {
                        ViseLog.e("请求失败");
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

}
