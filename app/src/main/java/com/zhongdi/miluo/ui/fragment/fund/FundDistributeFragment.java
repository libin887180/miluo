package com.zhongdi.miluo.ui.fragment.fund;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.market.FundDistributeAdapter;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.FundDividend;
import com.zhongdi.miluo.model.FundDividendResponse;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.widget.RecycleViewDivider;

import org.xutils.common.Callback;

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

public class FundDistributeFragment extends Fragment {
    Unbinder unbinder;
    View rootView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    FundDistributeAdapter adapter;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    Unbinder unbinder1;
    private String sellFundId;
    List<FundDividend> datas = new ArrayList<>();
    private int pageNum = 1;

    public static FundDistributeFragment newInstance(String info) {
        Bundle args = new Bundle();
        FundDistributeFragment fragment = new FundDistributeFragment();
        args.putString("sellFundId", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_fund_distribute, null);
            unbinder = ButterKnife.bind(this, rootView);
            sellFundId = getArguments().getString("sellFundId");
            initialize();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，
            // 要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            unbinder = ButterKnife.bind(this, rootView);
        }
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void getFundDividend(String sellFundId, String page) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId", sellFundId);
        map.put("pageSize ", MiluoConfig.DEFAULT_PAGESIZE + "");
        map.put("page", page);
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_DIVIDEND, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<FundDividendResponse>>() {
                    @Override
                    public void onSuccess(MResponse<FundDividendResponse> response, int requestCode) {
                        if (pageNum == 1) {
                            datas.clear();
                        }
                        if (response.getBody().getData().size() < MiluoConfig.DEFAULT_PAGESIZE) {
                            refreshLayout.setEnableLoadmore(false);
                        } else {
                            pageNum++;
                            refreshLayout.setEnableLoadmore(true);
                        }
                        datas.addAll(response.getBody().getData());
                        adapter.notifyDataSetChanged();
                        if (datas.size() == 0) {
                            stateLayout.showEmptyView();
                        } else {
                            stateLayout.showContentView();
                        }
                        refreshLayout.finishLoadmore();
                        refreshLayout.finishRefreshing();
                    }

                    @Override
                    public void onFailed(MResponse<FundDividendResponse> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
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

    private void initialize() {

        adapter = new FundDistributeAdapter(getActivity(), datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        getFundDividend(sellFundId, pageNum + "");
        stateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                getFundDividend(sellFundId, pageNum + "");
            }

            @Override
            public void loginClick() {

            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                getFundDividend(sellFundId, pageNum + "");
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                getFundDividend(sellFundId, pageNum + "");
            }
        });
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
        super.onDestroyView();
        unbinder1.unbind();

    }
}