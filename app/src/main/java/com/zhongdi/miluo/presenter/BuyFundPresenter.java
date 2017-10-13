package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.BeforeBuyInfo;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.BuyFundView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class BuyFundPresenter extends BasePresenter<BuyFundView> {
    public BuyFundPresenter(BuyFundView view) {
        super.attachView(view);
    }
    public void beforeBuyInit(String   fundCode) {
        Map<String, String> map = new HashMap<>();
        map.put("fundCode", fundCode );
        Callback.Cancelable post = netRequestUtil.post(URLConfig.FUND_BUY_BEFORE, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<BeforeBuyInfo>>() {
                    @Override
                    public void onSuccess(MResponse<BeforeBuyInfo> response, int requestCode) {
                        view.onDataSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<BeforeBuyInfo> response, int requestCode) {
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
