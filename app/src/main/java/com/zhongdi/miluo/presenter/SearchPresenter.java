package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.SearchFund;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.SearchView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class SearchPresenter extends BasePresenter<SearchView> {
    public SearchPresenter(SearchView view) {
        super.attachView(view);
    }

    public void getHotFund() {
        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = netRequestUtil.post(URLConfig.HOT_SEARCH, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<SearchFund>>>() {
                    @Override
                    public void onSuccess(MResponse<List<SearchFund>> response, int requestCode) {
                        view.onhotSearchSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<List<SearchFund>> response, int requestCode) {
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
    public void searchFund(String searchStr,int pageNum) {
        Map<String, String> map = new HashMap<>();
        map.put("fundName", searchStr);
        map.put("pageNumber", pageNum+"");
        Callback.Cancelable post = netRequestUtil.post(URLConfig.SEARCH_FUND, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<SearchFund>>>() {
                    @Override
                    public void onSuccess(MResponse<List<SearchFund>> response, int requestCode) {
                        view.onSearchSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<List<SearchFund>> response, int requestCode) {
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
