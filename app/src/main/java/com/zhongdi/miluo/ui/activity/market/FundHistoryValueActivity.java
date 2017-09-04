package com.zhongdi.miluo.ui.activity.market;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fingdo.statelayout.StateLayout;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.HistoryValueAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.SettingPresenter;
import com.zhongdi.miluo.view.SettingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FundHistoryValueActivity extends BaseActivity<SettingPresenter> implements SettingView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    HistoryValueAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_fund_history_value);
    }

    @Override
    protected SettingPresenter initPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected void initialize() {
        List<String> tabs = new ArrayList<>();
        tabs.add("1111111");
        tabs.add("222222222");
        tabs.add("3333333");
        tabs.add("3333333");
        tabs.add("3333333");
        tabs.add("3333333");
        tabs.add("3333333");
        tabs.add("3333333");
        tabs.add("3333333");
        tabs.add("3333333");
        tabs.add("3333333");
        adapter = new HistoryValueAdapter(mContext, tabs);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
    }

}
