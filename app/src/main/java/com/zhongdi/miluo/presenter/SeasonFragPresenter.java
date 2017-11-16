package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.RiskTestResult;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.SeasonFragmentView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/4.
 */

public class SeasonFragPresenter extends BasePresenter<SeasonFragmentView> {
    public SeasonFragPresenter(SeasonFragmentView view) {
        super.attachView(view);
    }

    public void getFundVal(String fundcode) {
        Map<String, String> map = new HashMap<>();
        map.put("fundcode", fundcode);
        map.put("flag ", "4");//（3月、4季、5半年、6一年）
        Callback.Cancelable post = netRequestUtil.post(URLConfig.FUND_VALUATION, map, 108,
                new NetRequestUtil.NetResponseListener<MResponse<RiskTestResult>>() {
                    @Override
                    public void onSuccess(MResponse<RiskTestResult> response, int requestCode) {

                    }

                    @Override
                    public void onFailed(MResponse<RiskTestResult> response, int requestCode) {
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
