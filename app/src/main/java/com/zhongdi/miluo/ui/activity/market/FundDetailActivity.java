package com.zhongdi.miluo.ui.activity.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

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
    private View sharePopView;
    private PopupWindow mCardPopupWindow;

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
        setupSharePopupWindow();
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
    private void showCardPopupWindow() {
        mCardPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
    // 显示弹窗
    public void setupSharePopupWindow() {
        // 初始化弹窗
        sharePopView = View.inflate(this, R.layout.pop_share_view, null);
        mCardPopupWindow = new PopupWindow(sharePopView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        sharePopView.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardPopupWindow.dismiss();
            }
        });
        // 设置动画
        mCardPopupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        // mPopupWindow.showAsDropDown(findViewById(R.id.head), 0, 0);
        mCardPopupWindow.setOutsideTouchable(true);
    }
    @OnClick({R.id.rl_fund_manager, R.id.rl_fund_notice, R.id.rl_premium, R.id.rl_archives, R.id.rl_fund_history,R.id.tv_buy,R.id.img_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_fund_manager:
                startActivity(new Intent(mContext, ManagerDetailActivity.class));
                break;
            case R.id.rl_fund_notice:
                startActivity(new Intent(mContext, FundNoticeActivity.class));
                break;
            case R.id.rl_premium:
                startActivity(new Intent(mContext, PremiumActivity.class));
                break;
            case R.id.rl_archives:
                startActivity(new Intent(mContext, FundAchivesActivity.class));
                break;
            case R.id.rl_fund_history:
                startActivity(new Intent(mContext, FundHistoryValueActivity.class));
                break;
            case R.id.tv_buy:
                startActivity(new Intent(mContext, BuyFundActivity.class));
                break;
            case R.id.img_title_right:
                showCardPopupWindow();
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mCardPopupWindow != null && mCardPopupWindow.isShowing()) {
                mCardPopupWindow.dismiss();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
