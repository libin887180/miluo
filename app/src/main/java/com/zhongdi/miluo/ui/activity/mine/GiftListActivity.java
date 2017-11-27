package com.zhongdi.miluo.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.GiftListAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.Prize;
import com.zhongdi.miluo.presenter.GetGiftListPresenter;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.view.GiftListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GiftListActivity extends BaseActivity<GetGiftListPresenter> implements GiftListView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    private GiftListAdapter listAdapter;
    List<Prize> cardList = new ArrayList<>();
    private int pageNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_recycle_list);
    }

    @Override
    protected GetGiftListPresenter initPresenter() {
        return new GetGiftListPresenter(this);
    }

    @Override
    protected void initialize() {
        setupHeadView();
        setupRefreshView();
        setupStatusView();
        listAdapter = new GiftListAdapter(mContext, cardList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<Prize>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, Prize prize, int position) {

                if (TextUtils.equals(prize.getStatus(),"1")) {
                    Intent intent = new Intent(mContext, ExchangeActivity.class);
                    intent.putExtra("prizeType", prize.getType());
                    intent.putExtra("prizeId", prize.getId());
                    startActivityForResult(intent,101);
                }
            }
        });
        presenter.getGiftList(pageNum, MiluoConfig.DEFAULT_PAGESIZE);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void setupRefreshView() {
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                presenter.getGiftList(pageNum, MiluoConfig.DEFAULT_PAGESIZE);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                presenter.getGiftList(pageNum, MiluoConfig.DEFAULT_PAGESIZE);
            }
        });
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
    public void reLogin() {
        Intent intent  = new Intent(mContext, QuickLoginActivity.class);
        startActivityForResult(intent, 301);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 301 && resultCode == Activity.RESULT_OK) {
            pageNum =1;
            presenter.getGiftList(pageNum, MiluoConfig.DEFAULT_PAGESIZE);
        }
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            pageNum = 1;
            presenter.getGiftList(pageNum, MiluoConfig.DEFAULT_PAGESIZE);
        }
    }

    @Override
    public void setupStatusView() {
        stateLayout.setUseAnimation(true);
//        stateLayout.setViewSwitchAnimProvider(new FadeScaleViewAnimProvider());
        stateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                pageNum = 1;
                presenter.getGiftList(pageNum, MiluoConfig.DEFAULT_PAGESIZE);
            }

            @Override
            public void loginClick() {
                ViseLog.i("11", "登录");
            }
        });

    }

    @Override
    public void setupHeadView() {
        title.setText("我的奖品");
    }

    @Override
    public void onDataSuccess(List<Prize> body) {

        if (pageNum == 1) {
            cardList.clear();
        }
        if (body.size() < MiluoConfig.DEFAULT_PAGESIZE) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            pageNum++;
            refreshLayout.setEnableLoadmore(true);
        }
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
        refreshLayout.setEnableRefresh(true);
        cardList.addAll(body);
        listAdapter.notifyDataSetChanged();
        if (cardList.size() == 0) {
            stateLayout.showEmptyView();
        } else {
            stateLayout.showContentView();
        }


    }
}
