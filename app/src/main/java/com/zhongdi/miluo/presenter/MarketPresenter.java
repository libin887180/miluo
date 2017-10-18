package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.FundType;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.MarketView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by libin on 2017/8/4.
 */

public class MarketPresenter extends BasePresenter<MarketView> {
    public MarketPresenter(MarketView view) {
        super.attachView(view);
    }

    public void getFundType() {
        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = netRequestUtil.post(URLConfig.FUND_TYPE, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<FundType>>>() {
                    @Override
                    public void onSuccess(MResponse<List<FundType>> response, int requestCode) {
                            view.initTabLayout(response.getBody());
                    }
                    @Override
                    public void onFailed(MResponse<List<FundType>> response, int requestCode) {
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
