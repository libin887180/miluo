package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.FundHistoryValueResponse;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.FundHistoryValueView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class FundHistoryValuePresenter extends BasePresenter<FundHistoryValueView> {
    public FundHistoryValuePresenter(FundHistoryValueView view) {
        super.attachView(view);
    }
    public void getFundHistoryValue(String fundId, int page,int pageSize) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId",fundId);
        map.put("page", page + "");
        map.put("pageSize", pageSize + "");
        Callback.Cancelable post = netRequestUtil.post(URLConfig.FUND_HISTORY_VALUE, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<FundHistoryValueResponse>>() {
                    @Override
                    public void onSuccess(MResponse<FundHistoryValueResponse> response, int requestCode) {
                        view.onDataSuccess(response.getBody().getData());
                    }

                    @Override
                    public void onFailed(MResponse<FundHistoryValueResponse> response, int requestCode) {
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
