package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.DealRecord;
import com.zhongdi.miluo.model.HomeAssetBean;
import com.zhongdi.miluo.model.MResponse;
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
        Map<String, String> map = new HashMap<>();
        map.put("fundcode", fundcode);
        Callback.Cancelable post = netRequestUtil.post(URLConfig.TRADE_DETAIL, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<List<HomeAssetBean>>>() {
                    @Override
                    public void onSuccess(MResponse<List<HomeAssetBean>> response, int requestCode) {
//                        view.onDataSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<List<HomeAssetBean>> response, int requestCode) {
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
     * 获取交易记录
     * @param pageIndex 页数
     * @param pageSize 每页条数
     */
    public void getTradRecords(final int pageIndex, int pageSize,String fundcode) {
        Map<String, String> map = new HashMap<>();
        map.put("pageIndex", pageIndex + "");
        map.put("pageSize", pageSize + "");
        map.put("fundcode",  fundcode);//基金代码
        Callback.Cancelable post = netRequestUtil.post(URLConfig.TRADE_RECORD_LIST, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<DealRecord>>>() {
                    @Override
                    public void onSuccess(MResponse<List<DealRecord>> response, int requestCode) {
                        view.onTradRecordsSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<List<DealRecord>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        view.showToast( response.getMsg());
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
