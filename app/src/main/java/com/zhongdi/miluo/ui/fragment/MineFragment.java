package com.zhongdi.miluo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.widget.RiseNumberTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MineFragment extends Fragment {


    ViewPager mViewPager;
    List<Fragment> mFragments;
    Toolbar mToolbar;

    List<String> mTitles = new ArrayList<>();
    public static MineFragment newInstance(String info) {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine2, null);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpage);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout)  view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        //获取到RiseNumberTextView对象
        RiseNumberTextView rnTextView = (RiseNumberTextView) view.findViewById(R.id.rise_tv);
        // 设置数据
        rnTextView.withNumber(892666.50f);
        // 设置动画播放时间
        rnTextView.setDuration(1000);
        // 开始播放动画
        rnTextView.start();

        return view;
    }



    private void setupViewPager(ViewPager viewPager) {
        mFragments=new ArrayList<>();
        mTitles.add("当前资产");
        mTitles.add("历史资产");
        for(int i=0;i<mTitles.size();i++){
            ListFragment listFragment = ListFragment.newInstance(mTitles.get(i));
            mFragments.add(listFragment);
        }
        MyFragmentPagerAdapter adapter =
                new MyFragmentPagerAdapter(getChildFragmentManager(),mFragments,mTitles);



        viewPager.setAdapter(adapter);
    }
}