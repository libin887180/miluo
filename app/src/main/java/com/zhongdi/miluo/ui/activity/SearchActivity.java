package com.zhongdi.miluo.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.SearchAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.SearchFund;
import com.zhongdi.miluo.presenter.SearchPresenter;
import com.zhongdi.miluo.view.SearchView;
import com.zhongdi.miluo.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchView {
    SearchAdapter adapter;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_hot)
    LinearLayout llHot;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    List<SearchFund> datas = new ArrayList<>();
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
                presenter.searchFund(etSearch.getText().toString());

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
        llHot.setVisibility(View.GONE);
        datas.clear();
        datas.addAll(body);
        adapter.notifyDataSetChanged();
    }
}
