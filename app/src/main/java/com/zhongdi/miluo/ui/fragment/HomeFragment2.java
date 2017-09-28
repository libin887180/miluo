package com.zhongdi.miluo.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.AwardedFundAdapter;
import com.zhongdi.miluo.adapter.GridImageAdapter;
import com.zhongdi.miluo.adapter.HomeSpecialityAdapter;
import com.zhongdi.miluo.adapter.HotInvestmentAdapter;
import com.zhongdi.miluo.adapter.MiluoUnderstandAdapter;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.ui.activity.login.InfomationsActivity;
import com.zhongdi.miluo.ui.activity.login.MessagesActivity;
import com.zhongdi.miluo.ui.activity.login.OpenAccountActivity;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.ui.activity.login.TestActivity;
import com.zhongdi.miluo.util.view.ActivityUtil;
import com.zhongdi.miluo.widget.MarqueeView;
import com.zhongdi.miluo.widget.MyRefreshView;
import com.zhongdi.miluo.widget.NoScrollGridView;
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

public class HomeFragment2 extends Fragment implements ObservableScrollView.OnObservableScrollViewListener {
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.head)
    RelativeLayout head;
    @BindView(R.id.mScrollView)
    ObservableScrollView mScrollView;
    @BindView(R.id.recycler_view)
    RecyclerView hor_recyclerView;
    @BindView(R.id.recyclerView_hot)
    RecyclerView recyclerViewHot;
    @BindView(R.id.recyclerView_awarded)
    RecyclerView recyclerViewAwarded;
    @BindView(R.id.recyclerView_understand)
    RecyclerView recyclerViewUnderStand;
    @BindView(R.id.upview1)
    MarqueeView upview1;
    @BindView(R.id.gv_activity)
    NoScrollGridView gvActivity;
    @BindView(R.id.rl_login_state)
    RelativeLayout rlLoginState;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    private View rootView;
    private List<String> scrollMsgs;
    private LinearLayoutManager mLayoutManager;


    public static HomeFragment2 newInstance(String info) {
        Bundle args = new Bundle();
        HomeFragment2 fragment = new HomeFragment2();
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_2, null);
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
            unbinder = ButterKnife.bind(this, rootView);
        }


        return rootView;
    }

    private void initView() {
        setupRefreshView();
        setUpMarqueeView();

        List<String> datas = new ArrayList<>();
        datas.add("1111111111111");
        datas.add("22222222222");
        datas.add("3333333333333");
        datas.add("4444444444444");
        //特色基金列表
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hor_recyclerView.setFocusable(false);
        hor_recyclerView.setLayoutManager(layoutManager);
        HomeSpecialityAdapter bankCardListAdapter = new HomeSpecialityAdapter(getActivity(), datas);
        hor_recyclerView.setAdapter(bankCardListAdapter);

        //热门基金列表
        recyclerViewHot.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerViewHot.setFocusable(false);
        HotInvestmentAdapter investmentAdapter = new HotInvestmentAdapter(getActivity(), datas);
        recyclerViewHot.setAdapter(investmentAdapter);

        //获奖基金列表
        AwardedFundAdapter awardedFundAdapter = new AwardedFundAdapter(getActivity(), datas);
        recyclerViewAwarded.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerViewAwarded.setFocusable(false);
        recyclerViewAwarded.setAdapter(awardedFundAdapter);
        //米罗懂你列表
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        MiluoUnderstandAdapter understandAdapter = new MiluoUnderstandAdapter(getActivity(), datas);
        recyclerViewUnderStand.setLayoutManager(manager);
        recyclerViewUnderStand.setFocusable(false);
        recyclerViewUnderStand.setAdapter(understandAdapter);

        head.getBackground().setAlpha(0);
        mScrollView.setOnObservableScrollViewListener(this);

        List<String> activitys = new ArrayList<>();
        activitys.add("1111111111111");
        activitys.add("22222222222");
        activitys.add("3333333333333");
        activitys.add("4444444444444");
        GridImageAdapter gridImageAdapter = new GridImageAdapter(getActivity(), activitys);
        gvActivity.setAdapter(gridImageAdapter);
        gvActivity.setFocusable(false);
    }

    private void setUpMarqueeView() {

        List<View> views2 = new ArrayList<>();
        views2.clear();//记得加这句话，不然可能会产生重影现象
        for (int i = 0; i < scrollMsgs.size(); i++) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) View.inflate(getActivity(), R.layout.item_view_single, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().startActivity(new Intent(getActivity(), InfomationsActivity.class));
                }
            });
            //进行对控件赋值
            tv1.setText(scrollMsgs.get(i).toString());
            //添加到循环滚动数组里面去
            views2.add(moreView);
        }

        upview1.setViews(views2);
    }

    private void initData() {

        scrollMsgs = new ArrayList<String>();
        scrollMsgs.add("股票基金奥斯卡几点来");
        scrollMsgs.add("奥术大师多");
        scrollMsgs.add("的范德萨发");
        btnLogin.setText("去测评");
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
        refreshLayout.setHeaderView(headerView);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK&&requestCode==102) {
            if (!TextUtils.isEmpty(SpCacheUtil.getInstance().getLoginAccount())) {
                if (SpCacheUtil.getInstance().getUserFundState() == MiluoConfig.UN_OPEN_ACCOUNT) {
                    btnLogin.setText("去开户");
                } else {
                    if (SpCacheUtil.getInstance().getUserTestLevel() == -1) {
                        btnLogin.setText("去测评");
                    } else {
                        rlLoginState.setVisibility(View.GONE);
                    }
                }
            }else{
                rlLoginState.setVisibility(View.VISIBLE);
                btnLogin.setText("立即登录");
            }
        }
    }

    @OnClick({R.id.btn_login, R.id.img_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (btnLogin.getText().equals("立即登录")) {
                    Intent intent = new Intent(getActivity(), QuickLoginActivity.class);
                    intent.putExtra(IntentConfig.SOURCE, IntentConfig.HOME_LOGIN);
                    startActivityForResult(intent,102);
                } else if (btnLogin.getText().equals("去开户")) {
                    ActivityUtil.startForwardActivity(getActivity(), OpenAccountActivity.class);
                } else if (btnLogin.getText().equals("去测评")) {
                    ActivityUtil.startForwardActivity(getActivity(), TestActivity.class);
                }

                break;
            case R.id.img_msg:
                startActivity(new Intent(getActivity(), MessagesActivity.class));
                break;
        }
    }
}