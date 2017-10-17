package com.zhongdi.miluo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;

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
//    private int selectTab = 0;

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

    private void setupViewPager(NoScrollViewPager viewPager) {
        viewPager.setScroll(false);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

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

//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
////                if (prevMenuItem != null) {
////                    prevMenuItem.setChecked(false);
////                } else {
////                    navigation.getMenu().getItem(0).setChecked(false);
////                }
//                if(position==2){
//                    Intent intent = new Intent(mContext, LoginActivity.class);
//                    startActivityForResult(intent, 101);
//                }else{
//                    navigation.getMenu().getItem(position).setChecked(true);
//                    selectTab = position;
//                }
////                prevMenuItem = navigation.getMenu().getItem(position);
//            }
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_market:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_self:
                    if (MyApplication.getInstance().isLogined) {
                        viewPager.setCurrentItem(2);
                        return true;
                    } else {
                        Intent intent = new Intent(mContext, QuickLoginActivity.class);
                        startActivityForResult(intent, 101);
                        return false;
                    }


                case R.id.navigation_mine:
                    if (MyApplication.getInstance().isLogined) {
                        viewPager.setCurrentItem(3);
                        return true;
                    } else {
                        Intent intent = new Intent(mContext, QuickLoginActivity.class);
                        startActivityForResult(intent, 101);
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
            case 0:
                break;
            case 1001:
                navigation.getMenu().getItem(0).setChecked(true);
                break;
        }
    }
}
