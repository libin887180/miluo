package com.zhongdi.miluo.ui.activity.login;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.InfomationssPresenter;
import com.zhongdi.miluo.ui.fragment.login.BeginnerFragment;
import com.zhongdi.miluo.ui.fragment.login.ImportantInfoFragment;
import com.zhongdi.miluo.ui.fragment.login.ResearchFragment;
import com.zhongdi.miluo.view.InfomationsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class InfomationsActivity extends BaseActivity<InfomationssPresenter> implements InfomationsView {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.title)
    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_message_list);
    }

    @Override
    protected InfomationssPresenter initPresenter() {
        return new InfomationssPresenter(this);
    }

    @Override
    protected void initialize() {
        tvTitle.setText("资讯列表");
        initTabLayout();
    }

    @Override
    public void initTabLayout() {
        List<String> tabs = new ArrayList<>();
        tabs.add("基金研报");
        tabs.add("基金导读");
        tabs.add("基金观点");
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
        adapter.addFragment(BeginnerFragment.newInstance("基金研报"));
        adapter.addFragment(ImportantInfoFragment.newInstance("基金导读"));
        adapter.addFragment(ResearchFragment.newInstance("基金观点"));
        viewPager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewPager);
    }

}
