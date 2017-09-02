package com.zhongdi.miluo.ui.activity.market;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.FundAchivesPresenter;
import com.zhongdi.miluo.ui.fragment.DemoFragment;
import com.zhongdi.miluo.ui.fragment.fund.FundGeneralFragment;
import com.zhongdi.miluo.view.FundArchivesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FundAchivesActivity extends BaseActivity<FundAchivesPresenter> implements FundArchivesView {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_fund_archives);
    }

    @Override
    protected FundAchivesPresenter initPresenter() {
        return new FundAchivesPresenter(this);
    }

    @Override
    protected void initialize() {
       initTabLayout();
    }

    @Override
    public void initTabLayout() {
        List<String> tabs = new ArrayList<>();
        tabs.add("基金概况");
        tabs.add("投资组合");
        tabs.add("分红配送");
        tablayout.removeAllTabs();
        for (int i = 0; i < tabs.size(); i++) {
            String itemName = tabs.get(i);
            if (i == 0) {
                tablayout.addTab(tablayout.newTab().setText(itemName), true);
            } else {
                tablayout.addTab(tablayout.newTab().setText(itemName), false);
            }
        }
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), tabs);
        adapter.addFragment(FundGeneralFragment.newInstance("基金概况"));
        adapter.addFragment(DemoFragment.newInstance("投资组合"));
        adapter.addFragment(DemoFragment.newInstance("分红配送"));
        viewPager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewPager);
    }
}
