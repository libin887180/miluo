package com.zhongdi.miluo.ui.activity.market;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.FundDetailPresenter;
import com.zhongdi.miluo.view.FundDetailView;
import com.zhongdi.miluo.widget.SegmentControl;

import butterknife.BindView;

public class FundDetailActivity extends BaseActivity<FundDetailPresenter> implements FundDetailView {

    @BindView(R.id.segment_control)
    SegmentControl segmentControl;

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
        segmentControl.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {


            }
        });

    }

}
