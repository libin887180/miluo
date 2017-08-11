package com.zhongdi.miluo.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.Toast;

import com.zhongdi.miluo.BottomNavigationViewHelper;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.ViewPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity2;
import com.zhongdi.miluo.ui.fragment.DemoFragment;
import com.zhongdi.miluo.ui.fragment.HomeFragment;
import com.zhongdi.miluo.ui.fragment.MarketFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity2 {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        setupViewPager(viewPager);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(HomeFragment.newInstance("首页"));
        adapter.addFragment(MarketFragment.newInstance("超市"));
        adapter.addFragment(DemoFragment.newInstance("自选"));
        adapter.addFragment(DemoFragment.newInstance("我的"));
        viewPager.setAdapter(adapter);
    }

    private void initView() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_market:
                    viewPager.setCurrentItem(1);
                    Toast.makeText(MainActivity.this, "market", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_self:
                    viewPager.setCurrentItem(2);
                    Toast.makeText(MainActivity.this, "self", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_mine:
                    viewPager.setCurrentItem(3);
                    Toast.makeText(MainActivity.this, "mine", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }

    };
}
