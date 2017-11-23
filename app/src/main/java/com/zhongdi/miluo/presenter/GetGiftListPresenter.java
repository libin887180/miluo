package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.Prize;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.GiftListView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class GetGiftListPresenter extends BasePresenter<GiftListView> {
    public GetGiftListPresenter(GiftListView view) {
        super.attachView(view);
    }


    public void getGiftList(int pageNumber,int pageSize) {
        view.showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("pageNumber",pageNumber+"");
        map.put("pageSize",pageSize+"");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.MY_PRIZE, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<List<Prize>>>() {
                    @Override
                    public void onSuccess(MResponse<List<Prize>> response, int requestCode) {
                        view.onDataSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<List<Prize>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        if(response.getCode().equals(ErrorCode.LOGIN_TIME_OUT)){
                            view.reLogin();
                        }else{
                            view.showToast(response.getMsg());
                        }

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
