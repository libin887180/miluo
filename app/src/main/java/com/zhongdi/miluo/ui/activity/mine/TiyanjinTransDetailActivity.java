package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.TiyanjinTransDetailPresenter;
import com.zhongdi.miluo.ui.activity.market.FundDetailActivity;
import com.zhongdi.miluo.view.TiyanjinTransDetailView;

import butterknife.BindView;
import butterknife.OnClick;

public class TiyanjinTransDetailActivity extends BaseActivity<TiyanjinTransDetailPresenter> implements TiyanjinTransDetailView {

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.tv_fund_name)
    TextView tvFundName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_risk)
    TextView tvRisk;
    @BindView(R.id.iv_invite_friends)
    ImageView ivInviteFriends;

    private String fundcode = "";
    private String fundId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fundcode = getIntent().getStringExtra("fundcode");
        binding(R.layout.activity_tiyanjin_trans_detail);
    }

    @Override
    protected TiyanjinTransDetailPresenter initPresenter() {
        return new TiyanjinTransDetailPresenter(this);
    }

    @Override
    protected void initialize() {
        refreshLayout.setEnableRefresh(false);//不可下拉刷新
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableOverScroll(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//                pageIndex=1;
//                presenter.getTradRecords(pageIndex, MiluoConfig.DEFAULT_PAGESIZE, fundcode);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refresh) {

            }
        });

        Glide.with(mContext).asGif().load(R.drawable.invite_friends).into(ivInviteFriends);

    }

    @Override
    public void dismissLoadingDialog() {
        getLoadingProgressDialog().dismiss();
    }

    @Override
    public void showLoadingDialog() {
        getLoadingProgressDialog().show();
    }


    @OnClick({R.id.tv_invite, R.id.rl_fund_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_invite:
//                Intent buyIntent = new Intent(mContext, BuyFundActivity.class);
//                buyIntent.putExtra("fundCode", fundcode);
//                startActivity(buyIntent);
                showToast("邀请好友");
                break;
            case R.id.rl_fund_info:
                if (!TextUtils.isEmpty(fundId)) {
                    Intent detail = new Intent(mContext, FundDetailActivity.class);
                    detail.putExtra("fundId", fundId);
                    detail.putExtra("fundcode", fundcode);
                    startActivity(detail);
                }
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return super.onKeyDown(keyCode, event);
    }
}
