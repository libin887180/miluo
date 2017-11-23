package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.HomeAssetBean;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.MyProperty;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.MineFragmentView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
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
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.MY_PROPERTY, map, 101,
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

    public void getPropertyList(int pageIndex, int pageSize) {
        Map<String, String> map = new HashMap<>();
        map.put("pageIndex", pageIndex + "");
        map.put("pageSize", pageSize + "");
        map.put("type", "0");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.MY_PROPERTY_LIST, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<List<HomeAssetBean>>>() {
                    @Override
                    public void onSuccess(MResponse<List<HomeAssetBean>> response, int requestCode) {
                        view.onCurrentPropertyList(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<List<HomeAssetBean>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        if(response.getCode().equals(ErrorCode.LOGIN_TIME_OUT)){
                            MyApplication.getInstance().isLogined =false;
                            view.reLogin();
                        }else{
                            view.showToast(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ViseLog.e(e);
                    }

                    @Override
                    public void onFinished() {
                        view.onRequestFinished();
                    }
                });
    }

    public void getHisPropertyList(int pageIndex, int pageSize) {
        Map<String, String> map = new HashMap<>();
        map.put("pageIndex", pageIndex + "");
        map.put("pageSize", pageSize + "");
        map.put("type", "1");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.MY_PROPERTY_LIST, map, 103,
                new NetRequestUtil.NetResponseListener<MResponse<List<HomeAssetBean>>>() {
                    @Override
                    public void onSuccess(MResponse<List<HomeAssetBean>> response, int requestCode) {
                        view.onHisPropertyList(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<List<HomeAssetBean>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        if(response.getCode().equals(ErrorCode.LOGIN_TIME_OUT)){
                            MyApplication.getInstance().isLogined =false;
                            view.reLogin();
                        }else{
                            view.showToast(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ViseLog.e(e);
                    }

                    @Override
                    public void onFinished() {
                        view.onRequestFinished();
                    }
                });
    }
}
