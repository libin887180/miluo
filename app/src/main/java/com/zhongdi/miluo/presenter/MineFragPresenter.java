package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.MyProperty;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.MineFragmentView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/4.
 */

public class MineFragPresenter extends BasePresenter<MineFragmentView> {
    public MineFragPresenter(MineFragmentView view) {
        super.attachView(view);
    }

    public void getProperty() {
        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = netRequestUtil.post(URLConfig.MY_PROPERTY, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<MyProperty>>() {
                    @Override
                    public void onSuccess(MResponse<MyProperty> response, int requestCode) {
                        view.onDataSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<MyProperty> response, int requestCode) {
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
