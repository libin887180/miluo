package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.FundNoticeResponse;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.FundNoticeView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class FundNoticePresenter extends BasePresenter<FundNoticeView> {
    public FundNoticePresenter(FundNoticeView view) {
        super.attachView(view);
    }

    public void getFundNotice(String fundId, int page) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId", fundId);
        map.put("page", page + "");
        map.put("pageSize", MiluoConfig.DEFAULT_PAGESIZE+"");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_NOTICE_LIST, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<FundNoticeResponse>>() {
                    @Override
                    public void onSuccess(MResponse<FundNoticeResponse> response, int requestCode) {
                        view.OnFundNoticeSuccess(response.getBody().getData());
                    }

                    @Override
                    public void onFailed(MResponse<FundNoticeResponse> response, int requestCode) {
                        ViseLog.e("请求失败");
//                        view.showToast(response.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onFinished() {
                        view.onRequestFinish();
                    }
                });
    }


}
