package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.PropertyDetail;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.TiyanjinTransDetailView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class TiyanjinTransDetailPresenter extends BasePresenter<TiyanjinTransDetailView> {
    public TiyanjinTransDetailPresenter(TiyanjinTransDetailView view) {
        super.attachView(view);
    }

    public void getPropertyDetail(String fundcode) {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("fundcode", fundcode);
        Callback.Cancelable post = netRequestUtil.post(URLConfig.TRADE_DETAIL, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<PropertyDetail>>() {
                    @Override
                    public void onSuccess(MResponse<PropertyDetail> response, int requestCode) {

                    }

                    @Override
                    public void onFailed(MResponse<PropertyDetail> response, int requestCode) {
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
