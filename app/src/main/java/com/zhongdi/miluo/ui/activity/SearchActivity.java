package com.zhongdi.miluo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.SearchAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.SearchFund;
import com.zhongdi.miluo.presenter.SearchPresenter;
import com.zhongdi.miluo.ui.activity.market.FundCurrencyDetailActivity;
import com.zhongdi.miluo.ui.activity.market.FundDetailActivity;
import com.zhongdi.miluo.view.SearchView;
import com.zhongdi.miluo.widget.ClearEditText;
import com.zhongdi.miluo.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchView {
    SearchAdapter adapter;
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.ll_hot)
    LinearLayout llHot;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    List<SearchFund> datas = new ArrayList<>();
    private int pageNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_search);
    }

    @Override
    protected SearchPresenter initPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected void initialize() {
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableRefresh(false);
        presenter.getHotFund();
        adapter = new SearchAdapter(mContext, datas);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        setListener();
    }

    @Override
    public void setListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pageNum = 1;
                presenter.searchFund(etSearch.getText().toString(), pageNum);

            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                presenter.searchFund(etSearch.getText().toString(), pageNum);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                presenter.searchFund(etSearch.getText().toString(), pageNum);
            }
        });
        adapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<SearchFund>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, SearchFund searchFund, int position) {
                Intent intent;
                if(searchFund.getFundtype().equals(MiluoConfig.HUOBI)){
                    intent  = new Intent(mContext, FundCurrencyDetailActivity.class);
                }else {
                    intent = new Intent(mContext, FundDetailActivity.class);
                }
                intent.putExtra("fundId",searchFund.getId());
                intent.putExtra("fundcode",searchFund.getFundcode());
                ViseLog.i("fundid-->"+searchFund.getId());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onhotSearchSuccess(List<SearchFund> body) {
        datas.clear();
        datas.addAll(body);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchSuccess(List<SearchFund> body) {
        if(pageNum==1){
            datas.clear();
        }
        if (body.size() < MiluoConfig.DEFAULT_PAGESIZE) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            pageNum++;
            refreshLayout.setEnableLoadmore(true);
        }
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
        llHot.setVisibility(View.GONE);
        refreshLayout.setEnableRefresh(true);
        datas.addAll(body);
        adapter.notifyDataSetChanged();
        if(datas.size()==0){
            stateLayout.showEmptyView();
        }else{
            stateLayout.showContentView();
        }
    }
}
