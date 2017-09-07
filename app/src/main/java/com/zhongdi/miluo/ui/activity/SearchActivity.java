package com.zhongdi.miluo.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.SearchAdapter;
import com.zhongdi.miluo.base.BaseActivity;
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
        List<String> datas = new ArrayList<>();
        datas.add("股票");
        datas.add("债券");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        datas.add("保本");
        adapter = new SearchAdapter(mContext, datas);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
    }

}
