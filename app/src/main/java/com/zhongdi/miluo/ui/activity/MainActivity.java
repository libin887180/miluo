package com.zhongdi.miluo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.zhongdi.miluo.BackHandlerHelper;
import com.zhongdi.miluo.BottomNavigationViewHelper;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.ViewPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity2;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.ui.fragment.CollectionFragment;
import com.zhongdi.miluo.ui.fragment.HomeFragment2;
import com.zhongdi.miluo.ui.fragment.MarketFragment;
import com.zhongdi.miluo.ui.fragment.MineFragment;
import com.zhongdi.miluo.widget.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity2 {

    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private MenuItem prevMenuItem;
    private int toTab = -1;
    private String TO;
    ViewPagerAdapter adapter;

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
//        getLoadingProgressDialog().show();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    private void setupViewPager(NoScrollViewPager viewPager) {
        viewPager.setScroll(false);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(HomeFragment2.newInstance("首页"));
        adapter.addFragment(MarketFragment.newInstance("超市"));
        adapter.addFragment(CollectionFragment.newInstance("自选"));
        adapter.addFragment(MineFragment.newInstance("我的"));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
                super.onBackPressed();
            }


        }
    }

    private void initView() {


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (!TextUtils.isEmpty(intent.getStringExtra("to"))) {
            if (intent.getStringExtra("to").equals("mine")) {
                navigation.getMenu().getItem(3).setChecked(true);
                viewPager.setCurrentItem(3);
                ((MineFragment) adapter.getItem(3)).fetchData();
            }
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    toTab = 0;
                    return true;
                case R.id.navigation_market:
                    viewPager.setCurrentItem(1);
                    toTab = 1;
                    return true;
                case R.id.navigation_self:
                    if (MyApplication.getInstance().isLogined) {
                        viewPager.setCurrentItem(2);
                        return true;
                    } else {
                        Intent intent = new Intent(mContext, QuickLoginActivity.class);
                        startActivityForResult(intent, 101);
                        toTab = 2;
                        return false;
                    }


                case R.id.navigation_mine:
                    if (MyApplication.getInstance().isLogined) {
                        viewPager.setCurrentItem(3);
                        return true;
                    } else {
                        Intent intent = new Intent(mContext, QuickLoginActivity.class);
                        startActivityForResult(intent, 101);
                        toTab = 3;
                        return false;
                    }
            }
            return false;
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (toTab != -1) {
                    if (toTab == 2) {
                        navigation.getMenu().getItem(2).setChecked(true);
                        viewPager.setCurrentItem(2);
                        ((CollectionFragment) adapter.getItem(2)).fetchData();
                    } else if (toTab == 3) {
                        navigation.getMenu().getItem(3).setChecked(true);
                        viewPager.setCurrentItem(3);
                        ((MineFragment) adapter.getItem(3)).fetchData();
                    }
                }
                break;
            case 1001://退出登录操作
                navigation.getMenu().getItem(0).setChecked(true);
                viewPager.setCurrentItem(0);
                break;
        }
    }
}
