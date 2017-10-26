package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.BankInfo;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.ExchangeView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class ExchangePresenter extends BasePresenter<ExchangeView> {
    public ExchangePresenter(ExchangeView view) {
        super.attachView(view);
    }


    public void getGiftList() {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = netRequestUtil.post(URLConfig.MY_BANK_CARDS, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<List<BankInfo>>>() {
                    @Override
                    public void onSuccess(MResponse<List<BankInfo>> response, int requestCode) {

//                        view.onDataSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<List<BankInfo>> response, int requestCode) {
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
