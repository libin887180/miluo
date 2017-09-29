package com.zhongdi.miluo.ui.activity.login;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.MessagesPresenter;
import com.zhongdi.miluo.ui.fragment.login.FundMessageFragment;
import com.zhongdi.miluo.ui.fragment.login.MiLuoNoticeFragment;
import com.zhongdi.miluo.view.MessagesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MessagesActivity extends BaseActivity<MessagesPresenter> implements MessagesView {
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
    protected MessagesPresenter initPresenter() {
        return new MessagesPresenter(this);
    }

    @Override
    protected void initialize() {
        initTabLayout();
    }

    @Override
    public void initTabLayout() {
        List<String> tabs = new ArrayList<>();
        tabs.add("基金消息");
        tabs.add("米罗公告");
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
        adapter.addFragment(FundMessageFragment.newInstance("基金消息"));
        adapter.addFragment(MiLuoNoticeFragment.newInstance("米罗公告"));
        viewPager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewPager);
    }
}
