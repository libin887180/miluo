package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.RiskTestResult;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.MonthFragmentView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/4.
 */

public class MonthFragPresenter extends BasePresenter<MonthFragmentView> {
    public MonthFragPresenter(MonthFragmentView view) {
        super.attachView(view);
    }

    public void getFundVal(String sellFundId ) {
        Map<String, String> map = new HashMap<>();
        map.put("flag ", "3");//（3月、4季、5半年、6一年）
        map.put("sellFundId ", sellFundId );
        Callback.Cancelable post = netRequestUtil.post(URLConfig.FUND_VALUATION, map, 107,
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
