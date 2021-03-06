package com.zhongdi.miluo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.HomePageAdapter;
import com.zhongdi.miluo.ui.activity.SearchActivity;
import com.zhongdi.miluo.ui.activity.login.MessagesActivity;
import com.zhongdi.miluo.ui.activity.login.RegisterActivity;
import com.zhongdi.miluo.util.GlideImageLoader;
import com.zhongdi.miluo.widget.MyRefreshView;
import com.zhongdi.miluo.widget.NOScollListView;
import com.zhongdi.miluo.widget.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class HomeFragment extends Fragment implements ObservableScrollView.OnObservableScrollViewListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.lv)
    NOScollListView lv;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.head)
    RelativeLayout head;
    @BindView(R.id.mScrollView)
    ObservableScrollView mScrollView;
    Unbinder unbinder1;

    private View rootView;
    private List<String> images;
    private List<String> scrollMsgs;
    private List<String> titles;
    private LinearLayoutManager mLayoutManager;


    public static HomeFragment newInstance(String info) {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.get("info").toString();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            unbinder = ButterKnife.bind(this, rootView);
            initData();
            initView();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，
            // 要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }

        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initView() {
        initBanner(rootView);
        setupRefreshView();
        lv = (NOScollListView) rootView.findViewById(R.id.lv);
        lv.setFocusable(false);
        HomePageAdapter homePageAdapter = new HomePageAdapter(getActivity(), titles, scrollMsgs);
        lv.setAdapter(homePageAdapter);
        head.getBackground().setAlpha(0);
        mScrollView.setOnObservableScrollViewListener(this);
    }

    private void initData() {

        scrollMsgs = new ArrayList<String>();
        scrollMsgs.add("股票基金奥斯卡几点来");
        scrollMsgs.add("奥术大师多");
        scrollMsgs.add("的范德萨发");
    }


    private void initBanner(View view) {
        images = new ArrayList<>();
        images.add("http://img02.yohoboys.com/contentimg/2017/08/04/17/0276499b03621c6eb67d6557c2cda3912e.gif");
        images.add("http://zxpic.imtt.qq.com/zxpic_imtt/2017/07/24/1900/originalimage/190135_14593482_3_640_427.jpg");
        images.add("http://zxpic.imtt.qq.com/zxpic_imtt/2017/07/24/1900/originalimage/190134_14593482_2_640_446.jpg");
        images.add("http://zxpic.imtt.qq.com/zxpic_imtt/2017/07/24/1900/originalimage/190133_14593482_1_640_397.jpg");
        titles = new ArrayList<>();
        titles.add("1111111111111");
        titles.add("22222222222");
        titles.add("3333333333333");
        titles.add("4444444444444");
        banner = (Banner) view.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.NUM_INDICATOR_TITLE);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void setupRefreshView() {
        MyRefreshView headerView = new MyRefreshView(getActivity());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        headerView.setOnPullListener(new MyRefreshView.OnPullListener() {
            @Override
            public void onPull(float fraction) {
                if (fraction > 0) {
                    head.setVisibility(View.GONE);
                } else {
                    head.setVisibility(View.VISIBLE);
                    head.getBackground().setAlpha(0);
                }
            }
        });
//        TextHeaderView headerView = (TextHeaderView) View.inflate(this,R.layout.header_tv,null);
        refreshLayout.setHeaderView(headerView);
//        LoadingView loadingView = new LoadingView(getActivity());
//        refreshLayout.setBottomView(loadingView);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
        ;
        unbinder1.unbind();
    }

    @OnClick({R.id.img_msg, R.id.et_search, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_msg:
                startActivity(new Intent(getActivity(), MessagesActivity.class));
                break;
            case R.id.et_search:

                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.btn_login:

                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
        }
    }


    @Override
    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
        float scale;
        if (t > head.getHeight() * 2.5f) {
            scale = 1;
        } else {
            scale = t / (head.getHeight() * 2.5f);
        }
        float alpha = (255 * scale);
        if (alpha < 35) {
            alpha = 0;
        }

        head.getBackground().setAlpha((int) alpha);

    }

}