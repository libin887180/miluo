package com.zhongdi.miluo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseFragment;
import com.zhongdi.miluo.model.MyProperty;
import com.zhongdi.miluo.presenter.MineFragPresenter;
import com.zhongdi.miluo.ui.activity.mine.SettingActivity;
import com.zhongdi.miluo.ui.activity.mine.TransactionsActivity;
import com.zhongdi.miluo.view.MineFragmentView;
import com.zhongdi.miluo.widget.RiseNumberTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MineFragment extends BaseFragment<MineFragPresenter> implements MineFragmentView {


    List<Fragment> mFragments;
    List<String> mTitles = new ArrayList<>();
    @BindView(R.id.tv_total_asset)
    RiseNumberTextView tvTotalAsset;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpage)
    ViewPager viewpage;

    Unbinder unbinder;
    @BindView(R.id.cb_visable)
    CheckBox cbVisable;
    @BindView(R.id.tv_yestoday_income)
    RiseNumberTextView tvYestodayIncome;
    @BindView(R.id.tv_total_income)
    RiseNumberTextView tvTotalIncome;
    MyFragmentPagerAdapter adapter;

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
        cbVisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean cheched) {
                setAssetVisable(cheched);
            }
        });
    }

    private void setAssetVisable(boolean visable) {
        tvTotalAsset.setNumVisable(visable);
        tvYestodayIncome.setNumVisable(visable);
        tvTotalIncome.setNumVisable(visable);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void fetchData() {
        refreshData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData(true);
    }

    public void refreshData() {
        if (MyApplication.getInstance().isLogined) {//登录了,查询数据
            presenter.getProperty();
            CurAssetFragment childAt = (CurAssetFragment) mFragments.get(viewpage.getCurrentItem());
            childAt.fetchData();
        }
    }


    @Override
    public void setupViewPager(ViewPager viewPager) {
        mFragments = new ArrayList<>();
        mTitles.clear();
        mTitles.add("当前资产");
        mTitles.add("历史资产");
        for (int i = 0; i < mTitles.size(); i++) {
            CurAssetFragment listFragment = CurAssetFragment.newInstance(mTitles.get(i));
            mFragments.add(listFragment);
        }
        adapter = new MyFragmentPagerAdapter(getChildFragmentManager(),
                mFragments, mTitles);

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpage);
    }

    @Override
    public void onDataSuccess(MyProperty property) {
        // 设置数据
        String   a="1519749.83";
        float aFloat = Float.parseFloat(a);
        tvTotalAsset.withNumber(Double.parseDouble(property.getTotalasset()));
        // 设置动画播放时间
        tvTotalAsset.setDuration(1000);
        // 开始播放动画
        tvTotalAsset.start();

        tvYestodayIncome.withNumber(Double.parseDouble(property.getDayincome()));
        tvYestodayIncome.setDuration(1000);
        // 开始播放动画
        tvYestodayIncome.start();

        tvTotalIncome.withNumber(Double.parseDouble(property.getAccumulatedincome()));
        tvTotalIncome.setDuration(1000);
        // 开始播放动画
        tvTotalIncome.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, rootView);//同样把 ButterKnife 抽出来
            initView(rootView);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，
            // 要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.tv_total_asset, R.id.tv_title_left, R.id.tv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_total_asset:
                break;
            case R.id.tv_title_left:
                Intent intent = new Intent(mContext, SettingActivity.class);
                getActivity().startActivityForResult(intent, 101);
                break;
            case R.id.tv_title_right:
                startActivity(new Intent(mContext, TransactionsActivity.class));
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}