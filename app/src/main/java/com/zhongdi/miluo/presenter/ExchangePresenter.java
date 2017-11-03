package com.zhongdi.miluo.presenter;

import android.text.TextUtils;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.ExchangeView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class ExchangePresenter extends BasePresenter<ExchangeView> {
    public ExchangePresenter(ExchangeView view) {
        super.attachView(view);
    }


    public void exchange( String  cardnum ,String  id ,String  type ,String  username ) {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("cardnum",cardnum);
        if(!TextUtils.isEmpty(id)){
            map.put("id",id);
        }
        map.put("type",type);
        map.put("username",username);
        Callback.Cancelable post = netRequestUtil.post(URLConfig.EX_CHANGE, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<Object>>() {
                    @Override
                    public void onSuccess(MResponse<Object> response, int requestCode) {
                        view.onDataSuccess();
                    }

                    @Override
                    public void onFailed(MResponse<Object> response, int requestCode) {
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
