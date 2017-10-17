package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BankCardListAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.BankInfo;
import com.zhongdi.miluo.presenter.BankCardListPresenter;
import com.zhongdi.miluo.view.BankCardListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BankCardListActivity extends BaseActivity<BankCardListPresenter> implements BankCardListView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    private BankCardListAdapter listAdapter;
    List<BankInfo> cardList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_recycle_list);
    }

    @Override
    protected BankCardListPresenter initPresenter() {
        return new BankCardListPresenter(this);
    }

    @Override
    protected void initialize() {
        setupHeadView();
        setupRefreshView();
        setupStatusView();
        listAdapter = new BankCardListAdapter(mContext, cardList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(listAdapter);

        presenter.getMyBankCards();

    }

    @OnClick(R.id.img_title_right)
    public void onViewClicked() {
        startActivity(new Intent(mContext, AddBankCardActivity.class));
    }

    @Override
    public void setupRefreshView() {
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);
//        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
//            @Override
//            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.finishRefreshing();
//                    }
//                }, 2000);
//            }
//
//            @Override
//            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.finishLoadmore();
//                    }
//                }, 2000);
//            }
//        });
    }

    @Override
    public void setupStatusView() {
        stateLayout.setUseAnimation(true);
//        stateLayout.setViewSwitchAnimProvider(new FadeScaleViewAnimProvider());
        stateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                ViseLog.i("11", "刷新");
            }

            @Override
            public void loginClick() {
                ViseLog.i("11", "登录");
            }
        });

    }

    @Override
    public void setupHeadView() {
        title.setText("我的银行卡");
    }

    @Override
    public void onDataSuccess(List<BankInfo> body) {

        cardList.clear();
        cardList.addAll(body);
        listAdapter.notifyDataSetChanged();
    }
}
