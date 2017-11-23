package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.BankInfo;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.ChooseBankView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class ChooseBankPresenter extends BasePresenter<ChooseBankView> {
    public ChooseBankPresenter(ChooseBankView view) {
        super.attachView(view);
    }

    public void getBankList() {
        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.QUERY_BANKS, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<BankInfo>>>() {
                    @Override
                    public void onSuccess(MResponse<List<BankInfo>> response, int requestCode) {
                        view.onSuccess(response.getBody());

                    }

                    @Override
                    public void onFailed(MResponse<List<BankInfo>> response, int requestCode) {
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
