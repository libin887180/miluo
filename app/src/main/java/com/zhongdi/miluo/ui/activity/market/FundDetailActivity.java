package com.zhongdi.miluo.ui.activity.market;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.FundDetailPresenter;
import com.zhongdi.miluo.view.FundDetailView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FundDetailActivity extends BaseActivity<FundDetailPresenter> implements FundDetailView {

    @BindView(R.id.tablayout)
    TabLayout tablayout;

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
        List<String> tabs = new ArrayList<>();
        tabs.add("估值");
        tabs.add("一个月");
        tabs.add("一季度");
        tabs.add("半年");
        tabs.add("一年");
        tablayout.removeAllTabs();
        for (int i = 0; i < tabs.size(); i++) {
            String itemName = tabs.get(i);
            if (i == 0) {
                tablayout.addTab(tablayout.newTab().setText(itemName), true);
            } else {
                tablayout.addTab(tablayout.newTab().setText(itemName), false);
            }
        }
        LinearLayout linearLayout = (LinearLayout) tablayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
    }

}
