package com.zhongdi.miluo.ui.activity.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.FundDetailPresenter;
import com.zhongdi.miluo.ui.fragment.fund.EstimateFragment;
import com.zhongdi.miluo.view.FundDetailView;
import com.zhongdi.miluo.widget.NoScrollViewPager;
import com.zhongdi.miluo.widget.SegmentControl;

import butterknife.BindView;
import butterknife.OnClick;

public class FundDetailActivity extends BaseActivity<FundDetailPresenter> implements FundDetailView {

    @BindView(R.id.segment_control)
    SegmentControl segmentControl;
    @BindView(R.id.mViewPager)
    NoScrollViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_fund_detail);
    }

    @Override
    protected FundDetailPresenter initPresenter() {
        return new FundDetailPresenter(this);
    }

    @Override
    protected void initialize() {
        mViewPager.setScroll(false);
        segmentControl.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {

                mViewPager.setCurrentItem(index);
            }
        });

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(EstimateFragment.newInstance("估值"));
        adapter.addFragment(EstimateFragment.newInstance("债券"));
        adapter.addFragment(EstimateFragment.newInstance("混合"));
        adapter.addFragment(EstimateFragment.newInstance("货币"));
        adapter.addFragment(EstimateFragment.newInstance("指数"));
        mViewPager.setAdapter(adapter);
        /**
         * 设置viewpager的选择事件
         */
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                segmentControl.setSelectedIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.rl_fund_manager, R.id.rl_fund_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_fund_manager:
                startActivity(new Intent(mContext,ManagerDetailActivity.class));
                break;
            case R.id.rl_fund_notice:
                startActivity(new Intent(mContext,FundNoticeActivity.class));
                break;
        }
    }
}
