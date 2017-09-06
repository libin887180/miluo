package com.zhongdi.miluo.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.adapter.market.IncreaseAdapter;
import com.zhongdi.miluo.adapter.market.SortAdapter;
import com.zhongdi.miluo.base.BaseFragment;
import com.zhongdi.miluo.presenter.MarketPresenter;
import com.zhongdi.miluo.view.MarketView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MarketFragment extends BaseFragment<MarketPresenter> implements MarketView {
    @BindView(R.id.head)
    RelativeLayout head;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.rl_orther)
    LinearLayout rlOrther;
    @BindView(R.id.rl_huobi)
    LinearLayout rlHuobi;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_increase)
    ImageView ivIncrease;
    Unbinder unbinder;
    RotateAnimation upAnimation;
    RotateAnimation downAnimation;
    private ListView lsvMore;

    private PopupWindow sortWindow;
    private SortAdapter sortAdapter;
    private ListView lvIncrease;
    private PopupWindow increaseWindow;
    private IncreaseAdapter increaseAdapter;
    private boolean isup;

    public static MarketFragment newInstance(String info) {
        Bundle args = new Bundle();
        MarketFragment fragment = new MarketFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MarketPresenter initPresenter() {
        return new MarketPresenter(this);
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

    @Override
    protected void initListener() {
        super.initListener();
        /**
         * 设置viewpager的选择事件
         */
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    rlHuobi.setVisibility(View.VISIBLE);
                    rlOrther.setVisibility(View.GONE);
                } else {
                    rlHuobi.setVisibility(View.GONE);
                    rlOrther.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         * 设置筛选的点击事件
         */
        lsvMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sortAdapter.setCheck(i);
                sortWindow.dismiss();

            }
        });

        lvIncrease.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                increaseAdapter.setCheck(i);
                increaseWindow.dismiss();

            }
        });
    }

    @Override
    protected void initView(View view) {
        initSortPop();
        initInCreasePop();
        initTabLayout();
        initAnimation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void initTabLayout() {

        List<String> tabs = new ArrayList<>();
        tabs.add("股票");
        tabs.add("债券");
        tabs.add("混合");
        tabs.add("货币");
        tabs.add("指数");
        tabs.add("保本");
        tablayout.removeAllTabs();
        for (int i = 0; i < tabs.size(); i++) {
            String itemName = tabs.get(i);
            if (i == 0) {
                tablayout.addTab(tablayout.newTab().setText(itemName), true);
            } else {
                tablayout.addTab(tablayout.newTab().setText(itemName), false);
            }
        }

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), tabs);
        adapter.addFragment(FundFragment.newInstance("股票"));
        adapter.addFragment(DemoFragment.newInstance("债券"));
        adapter.addFragment(DemoFragment.newInstance("混合"));
        adapter.addFragment(DemoFragment.newInstance("货币"));
        adapter.addFragment(DemoFragment.newInstance("指数"));
        adapter.addFragment(DemoFragment.newInstance("保本"));
        viewPager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initAnimation() {

        upAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(100);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(100);
        downAnimation.setFillAfter(true);
    }

    @Override
    public void doSomething() {
    }

    @Override
    public void initSortPop() {
        //   构建一个popupwindow的布局
        View popView = LayoutInflater.from(mContext).inflate(R.layout.pop_win_layout, null);
        lsvMore = (ListView) popView.findViewById(R.id.lsvMore);
        sortAdapter = new SortAdapter(mContext);
        lsvMore.setAdapter(sortAdapter);
        //   创建PopupWindow对象，指定宽度和高度
        sortWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //   设置动画
//        sortWindow.setAnimationStyle(R.style.popup_window_anim);
        //   设置背景颜色
//        sortWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        //   设置可以获取焦点
        sortWindow.setFocusable(true);
        //   设置可以触摸弹出框以外的区域
        sortWindow.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        sortWindow.update();
    }

    @Override
    public void initInCreasePop() {
        View popView = LayoutInflater.from(mContext).inflate(R.layout.pop_win_layout, null);
        lvIncrease = (ListView) popView.findViewById(R.id.lsvMore);
        increaseAdapter = new IncreaseAdapter(mContext);
        lvIncrease.setAdapter(increaseAdapter);
        //创建PopupWindow对象，指定宽度和高度
        increaseWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置动画
        //  sortWindow.setAnimationStyle(R.style.popup_window_anim);
        // 设置背景颜色
        //  sortWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        //  设置可以获取焦点
        increaseWindow.setFocusable(true);
        // 设置可以触摸弹出框以外的区域
        increaseWindow.setOutsideTouchable(true);
        increaseWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivIncrease.startAnimation(downAnimation);
            }
        });
        // TODO：更新popupwindow的状态
        increaseWindow.update();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_title_right, R.id.ll_increase})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title_right:
                if (sortWindow.isShowing()) {
                } else {
                    //   以下拉的方式显示，并且可以设置显示的位置
                    sortWindow.showAsDropDown(head);
                }
                break;
            case R.id.ll_increase:
                if (increaseWindow.isShowing()) {
                } else {
                    ivIncrease.startAnimation(upAnimation);
                    increaseWindow.showAsDropDown(rlOrther);
                }

                break;
        }
    }
}
