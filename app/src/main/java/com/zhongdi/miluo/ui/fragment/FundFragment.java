package com.zhongdi.miluo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.FundAdapter;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.Fund;
import com.zhongdi.miluo.model.FundListResponse;
import com.zhongdi.miluo.model.FundType;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.ui.activity.market.FundCurrencyDetailActivity;
import com.zhongdi.miluo.ui.activity.market.FundDetailActivity;
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
    private int pageNum = 1;
    private FundAdapter fundAdapter;

    public static FundFragment newInstance(FundType fundType) {
        Bundle args = new Bundle();
        FundFragment fragment = new FundFragment();
        args.putSerializable("fundType", fundType);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_fund, container,false);
            unbinder = ButterKnife.bind(this, rootView);
            fundType = (FundType) getArguments().getSerializable("fundType");
            initView(rootView);
//            initData();
        }
        else {
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


    public void initData() {
         pageNum = 1;
        if(fundType==null){
            fundType = (FundType) getArguments().getSerializable("fundType");
        }
        if(parentFragment==null){
            parentFragment =MarketFragment.getInstance();
        }
        getFunds(fundType.getDicno(), parentFragment.getRateType(), parentFragment.getSortType(), pageNum);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            initData();
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
        map.put("fundType", fundtype);
        map.put("page", page + "");
        map.put("rate", rate);
        if (!TextUtils.isEmpty(sort)) {
            map.put("sort", sort);
        }

        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_LIST, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<FundListResponse>>() {
                    @Override
                    public void onSuccess(MResponse<FundListResponse> response, int requestCode) {
                        if (pageNum == 1) {
                            funds.clear();
                        }
                        funds.addAll(response.getBody().getData());

                        if (response.getBody().getData().size() < MiluoConfig.DEFAULT_PAGESIZE) {
                            refreshLayout.setEnableLoadmore(false);
                        } else {
                            refreshLayout.setEnableLoadmore(true);
                            pageNum++;
                        }
                        fundAdapter.notifyDataSetChanged();
                        if(funds.size()==0){
                            stateLayout.showNoNetworkView();
                        }
                    }

                    @Override
                    public void onFailed(MResponse<FundListResponse> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText(getActivity(), "onError", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinished() {
                        refreshLayout.finishLoadmore();
                        refreshLayout.finishRefreshing();
//                        Toast.makeText(getActivity(), "onFinished", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView(View  rootView) {

        parentFragment = (MarketFragment) getParentFragment();
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                getFunds(fundType.getDicno(), parentFragment.getRateType(), parentFragment.getSortType(), pageNum);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                getFunds(fundType.getDicno(), parentFragment.getRateType(), parentFragment.getSortType(), pageNum);
            }
        });
        rvFunds.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        fundAdapter = new FundAdapter(getActivity(), funds);
        fundAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<Fund>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, Fund fund, int position) {
                Intent intent;
                if(fund.getFundType().equals(MiluoConfig.HUOBI)||fund.getFundType().equals(MiluoConfig.DUANQI)){
                     intent  = new Intent(getActivity(), FundCurrencyDetailActivity.class);
                }else {
                    intent = new Intent(getActivity(), FundDetailActivity.class);
                }
                intent.putExtra("fundId",fund.getId());
                intent.putExtra("fundcode",fund.getFundCode());
                ViseLog.i("fundid-->"+fund.getId());
                startActivity(intent);
            }
        });
        rvFunds.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFunds.setAdapter(fundAdapter);
        stateLayout.setUseAnimation(true);
//        stateLayout.setViewSwitchAnimProvider(new FadeScaleViewAnimProvider());
        stateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
//                pageNum =1;
//                getFunds(fundType.getDicno(), parentFragment.getRateType(), parentFragment.getSortType(), pageNum);
                stateLayout.showContentView();
            }

            @Override
            public void loginClick() {
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