package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.FundValuationResponse;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.HalfYearFragmentView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/4.
 */

public class HalfYearFragPresenter extends BasePresenter<HalfYearFragmentView> {
    public HalfYearFragPresenter(HalfYearFragmentView view) {
        super.attachView(view);
    }

    public void getFundVal(String sellFundId,String  fundCode) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId", sellFundId);
        map.put("flag", "5");//（3月、4季、5半年、6一年）
        map.put("fundFlag", "05");//（3月、4季、5半年、6一年）
        map.put("fundCode", fundCode );
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_VALUATION, map, 109,
                new NetRequestUtil.NetResponseListener<MResponse<FundValuationResponse>>() {
                    @Override
                    public void onSuccess(MResponse<FundValuationResponse> response, int requestCode) {
                        view.onDataSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<FundValuationResponse> response, int requestCode) {
                        view.showToast(response.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        ViseLog.e(e);
                    }

                    @Override
                    public void onFinished() {
                    }
                });
    }


}
