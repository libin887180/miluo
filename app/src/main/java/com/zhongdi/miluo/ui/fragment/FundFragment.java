package com.zhongdi.miluo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.FundAdapter;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.Fund;
import com.zhongdi.miluo.model.FundListResponse;
import com.zhongdi.miluo.model.FundType;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.widget.RecycleViewDivider;

import org.xutils.common.Callback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class FundFragment extends Fragment {
    @BindView(R.id.rv_funds)
    RecyclerView rvFunds;
    Unbinder unbinder;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private FundType fundType;
    private MarketFragment parentFragment;
    private View rootView;
    private List<Fund> funds = new ArrayList<>();
    private int pageNum;

    public static FundFragment newInstance(FundType fundType) {
        Bundle args = new Bundle();
        FundFragment fragment = new FundFragment();
        args.putSerializable("fundType", fundType);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_fund, null);
            unbinder = ButterKnife.bind(this, rootView);
            fundType = (FundType) getArguments().getSerializable("fundType");
            initView();
            initData();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，
            // 要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }


    private void initData() {

//        getFunds(fundType.getDicno(),parentFragment.getRateType(),parentFragment.getSortType(),pageNum);
        String result = "{\"body\":{\"total\":1624,\"data\":[{\"netvalue\":1.48,\"fundtype\":2,\"semesterrate\":0,\"fundcode\":\"150166\",\"weekrate\":0,\"fundname\":\"国富恒利分级B\",\"seasonrate\":0,\"yearyld\":0,\"dayrate\":-32.52,\"monthrate\":0,\"yearrate\":0,\"id\":6555,\"valuedate\":\"2017-01-09 00:03:00\",\"add_time\":1505802495000},{\"netvalue\":0.97,\"fundtype\":2,\"semesterrate\":0,\"fundcode\":\"150189\",\"weekrate\":0,\"fundname\":\"招商可转债B\",\"seasonrate\":0,\"yearyld\":0,\"dayrate\":-1.13,\"monthrate\":0,\"yearrate\":0,\"id\":10711,\"valuedate\":\"2017-01-06 00:09:00\",\"add_time\":1505802722000}]},\"code\":\"0\",\"msg\":\"success\"}";
        Type type = new TypeToken<MResponse<FundListResponse>>() {
        }.getType();
        MResponse<FundListResponse> response = new Gson().fromJson(result, type);
        if (TextUtils.equals(response.getCode(), ErrorCode.SUCCESS)) {

            if (pageNum == 1) {
                funds.clear();
            } else {
                funds.addAll(response.getBody().getData());
            }
            if(response.getBody().getData().size()<MiluoConfig.DEFAULT_PAGESIZE){
                refreshLayout.setEnableLoadmore(false);
            }

        }
    }

    /**
     * @param fundtype 基金类型
     * @param rate     日涨幅，周涨幅
     * @param sort     排序方式
     * @param page     页数
     */
    private void getFunds(String fundtype, String rate, String sort, int page) {
        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_LIST, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<FundListResponse>>() {
                    @Override
                    public void onSuccess(MResponse<FundListResponse> response, int requestCode) {
                        if (TextUtils.equals(response.getCode(), ErrorCode.SUCCESS)) {
                            ViseLog.w(response.getBody());

                        } else {


                        }
                    }

                    @Override
                    public void onFailed(MResponse<FundListResponse> response, int requestCode) {
                        ViseLog.e("请求失败");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    private void initView() {

        parentFragment = (MarketFragment) getParentFragment();
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
        rvFunds.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        FundAdapter fundAdapter = new FundAdapter(getActivity(), funds);
        rvFunds.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFunds.setAdapter(fundAdapter);
        stateLayout.setUseAnimation(true);
//        stateLayout.setViewSwitchAnimProvider(new FadeScaleViewAnimProvider());
        stateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                Log.i("11", "刷新");
            }

            @Override
            public void loginClick() {
                Log.i("11", "登录");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
    }
}