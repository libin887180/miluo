package com.zhongdi.miluo.ui.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.FundAchivesPresenter;
import com.zhongdi.miluo.ui.fragment.mine.DealingFragment;
import com.zhongdi.miluo.ui.fragment.mine.FinishedTransFragment;
import com.zhongdi.miluo.view.FundArchivesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TransactionsActivity extends BaseActivity<FundAchivesPresenter> implements FundArchivesView {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.title)
    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_transaction_list);
    }

    @Override
    protected FundAchivesPresenter initPresenter() {
        return new FundAchivesPresenter(this);
    }

    @Override
    protected void initialize() {
        tvTitle.setText("全部交易");
        initTabLayout();
    }

    @Override
    public void initTabLayout() {
        List<String> tabs = new ArrayList<>();
        tabs.add("进行中");
        tabs.add("已完成");
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
        adapter.addFragment(DealingFragment.newInstance("进行中"));
        adapter.addFragment(FinishedTransFragment.newInstance("已完成"));
        viewPager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewPager);
    }
}
