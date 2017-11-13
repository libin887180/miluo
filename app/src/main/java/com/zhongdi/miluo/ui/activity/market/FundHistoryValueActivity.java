package com.zhongdi.miluo.ui.activity.market;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.CurrencyHistoryValueAdapter;
import com.zhongdi.miluo.adapter.HistoryValueAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.HistoryValue;
import com.zhongdi.miluo.presenter.FundHistoryValuePresenter;
import com.zhongdi.miluo.view.FundHistoryValueView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FundHistoryValueActivity extends BaseActivity<FundHistoryValuePresenter> implements FundHistoryValueView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    HistoryValueAdapter adapter;
    CurrencyHistoryValueAdapter historyValueAdapter;
    List<HistoryValue> datas = new ArrayList<>();
    @BindView(R.id.ll_value)
    LinearLayout llValue;
    @BindView(R.id.ll_his)
    LinearLayout llHis;
    private String sellFundId;
    private int pageNum = 1;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sellFundId = getIntent().getStringExtra("fundId");
        from = getIntent().getStringExtra(IntentConfig.SOURCE);
        binding(R.layout.activity_fund_history_value);
    }

    @Override
    protected FundHistoryValuePresenter initPresenter() {
        return new FundHistoryValuePresenter(this);
    }

    @Override
    protected void initialize() {

        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                presenter.getFundHistoryValue(sellFundId, pageNum, MiluoConfig.DEFAULT_PAGESIZE);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                presenter.getFundHistoryValue(sellFundId, pageNum, MiluoConfig.DEFAULT_PAGESIZE);
            }
        });
        stateLayout.setUseAnimation(true);
//        stateLayout.setViewSwitchAnimProvider(new FadeScaleViewAnimProvider());
        stateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                pageNum = 1;
                presenter.getFundHistoryValue(sellFundId, pageNum, MiluoConfig.DEFAULT_PAGESIZE);
            }

            @Override
            public void loginClick() {
            }
        });
        adapter = new HistoryValueAdapter(mContext, datas);
        historyValueAdapter = new CurrencyHistoryValueAdapter(mContext, datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if (TextUtils.isEmpty(from)) {
            llHis.setVisibility(View.GONE);
            llValue.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
        } else {
            llHis.setVisibility(View.VISIBLE);
            llValue.setVisibility(View.GONE);
            recyclerView.setAdapter(historyValueAdapter);
        }
        presenter.getFundHistoryValue(sellFundId, pageNum, MiluoConfig.DEFAULT_PAGESIZE);
    }

    @Override
    public void dismissLoadingDialog() {
        getLoadingProgressDialog().dismiss();
    }

    @Override
    public void showLoadingDialog() {
        getLoadingProgressDialog().show();
    }

    @Override
    public void onDataSuccess(List<HistoryValue> hisdata) {
        if (pageNum == 1) {
            datas.clear();
        }
        datas.addAll(hisdata);

        if (hisdata.size() < MiluoConfig.DEFAULT_PAGESIZE) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
            pageNum++;
        }
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();

        if (TextUtils.isEmpty(from)) {
            adapter.notifyDataSetChanged();
        } else {
            historyValueAdapter.notifyDataSetChanged();
        }

        if (datas.size() == 0) {
            stateLayout.showEmptyView();
        } else {
            stateLayout.showContentView();
        }
    }
}
