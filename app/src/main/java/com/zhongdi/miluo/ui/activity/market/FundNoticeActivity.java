package com.zhongdi.miluo.ui.activity.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.FundNoticeAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.FundNotice;
import com.zhongdi.miluo.presenter.FundNoticePresenter;
import com.zhongdi.miluo.view.FundNoticeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FundNoticeActivity extends BaseActivity<FundNoticePresenter> implements FundNoticeView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    private FundNoticeAdapter noticeAdapter;
    List<FundNotice> notices = new ArrayList<>();
    private String sellFundId;
    private int pageNum = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sellFundId = getIntent().getStringExtra("fundId");
        binding(R.layout.activity_fund_notice);
    }

    @Override
    protected FundNoticePresenter initPresenter() {
        return new FundNoticePresenter(this);
    }

    @Override
    protected void initialize() {
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                presenter.getFundNotice(sellFundId,pageNum);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                presenter.getFundNotice(sellFundId,pageNum);
            }
        });
        stateLayout.setUseAnimation(true);
//        stateLayout.setViewSwitchAnimProvider(new FadeScaleViewAnimProvider());
        stateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                pageNum =1;
                presenter.getFundNotice(sellFundId,pageNum);
            }

            @Override
            public void loginClick() {
            }
        });
        presenter.getFundNotice(sellFundId,pageNum);
        noticeAdapter = new FundNoticeAdapter(mContext, notices);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(noticeAdapter);
        noticeAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<FundNotice>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, FundNotice notice, int position) {
                Intent intent= new Intent(mContext,FundNoticeDetailActivity.class);
                intent.putExtra("url",notice.getAttachment());
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnFundNoticeSuccess(List<FundNotice> datas) {
        if (pageNum == 1) {
            notices.clear();
        }
        notices.addAll(datas);

        if (datas.size() < MiluoConfig.DEFAULT_PAGESIZE) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
            pageNum++;
        }
        noticeAdapter.notifyDataSetChanged();
        if(notices.size()==0){
            stateLayout.showEmptyView();
        }
    }

    @Override
    public void onRequestFinish() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }
}
