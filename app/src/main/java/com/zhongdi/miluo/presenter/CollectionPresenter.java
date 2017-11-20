package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.OptionalFundResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.CollectionView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/4.
 */

public class CollectionPresenter extends BasePresenter<CollectionView> {
    public CollectionPresenter(CollectionView view) {
        super.attachView(view);
    }

    public void getOptionalFund(String rate, int page) {
        Map<String, String> map = new HashMap<>();
        map.put("page", page+"");
        map.put("rate", rate);
        Callback.Cancelable post = netRequestUtil.post(URLConfig.OPTIONAL_FUND, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<OptionalFundResponse>>() {
                    @Override
                    public void onSuccess(MResponse<OptionalFundResponse> response, int requestCode) {

                        view.onDataSuccess(response.getBody().getData());
                    }

                    @Override
                    public void onFailed(MResponse<OptionalFundResponse> response, int requestCode) {
                        ViseLog.e("请求失败");
                        view.showToast(response.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {
view.onRequestFinished();
                    }
                });
    }

}
