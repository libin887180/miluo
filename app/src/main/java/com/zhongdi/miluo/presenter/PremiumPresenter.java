package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.RateResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.PremiumView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class PremiumPresenter extends BasePresenter<PremiumView> {
    public PremiumPresenter(PremiumView view) {
        super.attachView(view);
    }

    public void getFundRate(String fundId) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId", fundId);
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_RATE, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<RateResponse>>() {
                    @Override
                    public void onSuccess(MResponse<RateResponse> response, int requestCode) {
                        view.onDateSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<RateResponse> response, int requestCode) {
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
