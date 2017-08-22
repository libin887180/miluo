package com.zhongdi.miluo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseFragment;
import com.zhongdi.miluo.presenter.MineFragPresenter;
import com.zhongdi.miluo.ui.activity.mine.SettingActivity;
import com.zhongdi.miluo.view.MineFragmentView;
import com.zhongdi.miluo.widget.RiseNumberTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MineFragment extends BaseFragment<MineFragPresenter> implements MineFragmentView {


    List<Fragment> mFragments;
    List<String> mTitles = new ArrayList<>();
    @BindView(R.id.rise_tv)
    RiseNumberTextView rnTextView;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpage)
    ViewPager viewpage;


    public static MineFragment newInstance(String info) {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected MineFragPresenter initPresenter() {
        return new MineFragPresenter(this);
    }


    @Override
    protected void initView(View view) {
        setupViewPager(viewpage);

        // 设置数据
        rnTextView.withNumber(892666.50f);
        // 设置动画播放时间
        rnTextView.setDuration(1000);
        // 开始播放动画
        rnTextView.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine2;
    }

    @Override
    public void fetchData() {

    }


    @Override
    public void setupViewPager(ViewPager viewPager) {
        mFragments = new ArrayList<>();
        mTitles.add("当前资产");
        mTitles.add("历史资产");
        for (int i = 0; i < mTitles.size(); i++) {
            CurAssetFragment listFragment = CurAssetFragment.newInstance(mTitles.get(i));
            mFragments.add(listFragment);
        }
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(),
                mFragments, mTitles);

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(),container, false);
            unbinder=  ButterKnife.bind(this, rootView);//同样把 ButterKnife 抽出来
            initView(rootView);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，
            // 要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @OnClick({R.id.rise_tv, R.id.tv_title_left, R.id.tv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rise_tv:
                break;
            case R.id.tv_title_left:

                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.tv_title_right:
                break;
        }
    }


}