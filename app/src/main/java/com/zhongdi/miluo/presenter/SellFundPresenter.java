package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.BuyResponse;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.SellResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.SellFundView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class SellFundPresenter extends BasePresenter<SellFundView> {
    public SellFundPresenter(SellFundView view) {
        super.attachView(view);
    }

    public void beforeSellInit(String fundCode) {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("fundCode", fundCode);
        Callback.Cancelable post = netRequestUtil.post(URLConfig.FUND_SELL_BEFORE, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<SellResponse>>() {
                    @Override
                    public void onSuccess(MResponse<SellResponse> response, int requestCode) {
                        view.onDataSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<SellResponse> response, int requestCode) {
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

    public void sellFund(String fundCode, String tradepwd, String transshare) {

        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("productid", fundCode);
        map.put("tradepwd", tradepwd);
        map.put("transshare", transshare);
        map.put("redeembizcode", "024"); //赎回方式024-普通赎回098-快速赎回n
        Callback.Cancelable post = netRequestUtil.post(URLConfig.SELL_FUND, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<BuyResponse>>() {
                    @Override
                    public void onSuccess(MResponse<BuyResponse> response, int requestCode) {
                        view.onSellSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<BuyResponse> response, int requestCode) {
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
}
