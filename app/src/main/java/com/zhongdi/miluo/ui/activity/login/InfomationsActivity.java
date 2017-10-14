package com.zhongdi.miluo.ui.activity.login;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.InfomationNote;
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
        tabs.add("小白学基");
        tabs.add("基金要闻");
        tabs.add("投资研究");
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
        adapter.addFragment(BeginnerFragment.newInstance("小白学基"));
        adapter.addFragment(ImportantInfoFragment.newInstance("基金要闻"));
        adapter.addFragment(ResearchFragment.newInstance("投资研究"));
        viewPager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewPager);
    }

}
