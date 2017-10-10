package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.FundDetail;
import com.zhongdi.miluo.model.FundManagerInfo;
import com.zhongdi.miluo.model.FundNotice;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.FundDetailView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class FundDetailPresenter extends BasePresenter<FundDetailView> {
    public FundDetailPresenter(FundDetailView view) {
        super.attachView(view);
    }

    public void getFundDetail(String fundId) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId", fundId);
        Callback.Cancelable post = netRequestUtil.post(URLConfig.FUND_DETAIL, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<FundDetail>>() {
                    @Override
                    public void onSuccess(MResponse<FundDetail> response, int requestCode) {
                        view.OnDataSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<FundDetail> response, int requestCode) {
                        ViseLog.e("请求失败");
//                        view.showToast(response.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }
    public void getFundManagerInfo(String fundId) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId", "2215");
        Callback.Cancelable post = netRequestUtil.post(URLConfig.FUND_MANAGER, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<FundManagerInfo>>() {
                    @Override
                    public void onSuccess(MResponse<FundManagerInfo> response, int requestCode) {
                        view.OnFundManagerSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<FundManagerInfo> response, int requestCode) {
                        ViseLog.e("请求失败");
//                        view.showToast(response.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }
    public void getFundNotice(String fundId) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId", "2215");
        Callback.Cancelable post = netRequestUtil.post(URLConfig.FUND_DETAIL_NOTICE, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<FundNotice>>() {
                    @Override
                    public void onSuccess(MResponse<FundNotice> response, int requestCode) {
                        view.OnFundNoticeSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<FundNotice> response, int requestCode) {
                        ViseLog.e("请求失败");
//                        view.showToast(response.getMsg());
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
