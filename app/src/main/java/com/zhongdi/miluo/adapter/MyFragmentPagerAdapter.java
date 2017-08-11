package com.zhongdi.miluo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyu on 2017/1/23.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitleList;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private Context mContext;

    public MyFragmentPagerAdapter(Context context, FragmentManager fm,
                                  List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.mContext = context;
        this.mTitleList = titleList;
    }
    public MyFragmentPagerAdapter(Context context, FragmentManager fm,
                                  List<String> titleList) {
        super(fm);
        this.mContext = context;
        this.mTitleList = titleList;
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
