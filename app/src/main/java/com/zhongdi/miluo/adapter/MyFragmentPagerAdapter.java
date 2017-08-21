package com.zhongdi.miluo.adapter;

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
    public MyFragmentPagerAdapter(FragmentManager fm) {
        this(fm, null);
    }

    public MyFragmentPagerAdapter(FragmentManager fm,
                                  List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        this.fragmentList = fragmentList;
        this.mTitleList = titleList;
    }


    public MyFragmentPagerAdapter(FragmentManager fm,List<String> titleList) {
        this(fm, null,titleList);
    }
    public boolean isEmpty() {
        return fragmentList == null;

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
