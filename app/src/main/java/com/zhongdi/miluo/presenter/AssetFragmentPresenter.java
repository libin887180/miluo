package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.HomeAssetBean;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.AssetFragmentView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by libin on 2017/8/4.
 */

public class AssetFragmentPresenter extends BasePresenter<AssetFragmentView> {
    public AssetFragmentPresenter(AssetFragmentView view) {
        super.attachView(view);
    }

    public void getPropertyList(int pageIndex, int pageSize, int type) {
        Map<String, String> map = new HashMap<>();
        map.put("pageIndex", pageIndex + "");
        map.put("pageSize", pageSize + "");
        map.put("type", type + "");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.MY_PROPERTY_LIST, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<List<HomeAssetBean>>>() {
                    @Override
                    public void onSuccess(MResponse<List<HomeAssetBean>> response, int requestCode) {
                        view.onDataSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<List<HomeAssetBean>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        view.showToast(response.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        ViseLog.e(e);
                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

}
