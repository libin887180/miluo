package com.zhongdi.miluo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.FragmentBackHandler;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.CollectionAdapter;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.market.CollectIncreaseAdapter;
import com.zhongdi.miluo.base.BaseFragment;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.eventbus.MessageEvent;
import com.zhongdi.miluo.model.OptionalFund;
import com.zhongdi.miluo.presenter.CollectionPresenter;
import com.zhongdi.miluo.ui.activity.SearchActivity;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.ui.activity.market.FundCurrencyDetailActivity;
import com.zhongdi.miluo.ui.activity.market.FundDetailActivity;
import com.zhongdi.miluo.view.CollectionView;
import com.zhongdi.miluo.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zhongdi.miluo.R.id.lsvMore;

/**
 * Created by Administrator on 2017/7/24.
 */

public class CollectionFragment extends BaseFragment<CollectionPresenter> implements CollectionView, FragmentBackHandler {
    @BindView(R.id.rl_orther)
    LinearLayout rlOrther;
    @BindView(R.id.iv_increase)
    ImageView ivIncrease;
    Unbinder unbinder;
    RotateAnimation upAnimation;
    RotateAnimation downAnimation;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    @BindView(R.id.tv_increase)
    TextView tvIncrease;
    List<OptionalFund> optionalFunds = new ArrayList<>();//自选基金列表
    private ListView lvIncrease;
    private PopupWindow increaseWindow;
    private CollectIncreaseAdapter increaseAdapter;
    private int pageNum = 1;
    CollectionAdapter fundAdapter;
    private String rateType = "";//默认日涨幅
    View emptyView;

    public static CollectionFragment newInstance(String info) {
        Bundle args = new Bundle();
        CollectionFragment fragment = new CollectionFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected CollectionPresenter initPresenter() {
        return new CollectionPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, rootView);//同样把 ButterKnife 抽出来
            EventBus.getDefault().register(this);
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

    //    @Override
//    public boolean prepareFetchData(boolean forceUpdate) {
//        return super.prepareFetchData(true);
//    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        ViseLog.i("******");
        pageNum=1;
        presenter.getOptionalFund(rateType, pageNum);
    }

    @Override
    protected void initListener() {
        super.initListener();

        lvIncrease.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                increaseAdapter.setCheck(i);
                increaseWindow.dismiss();
                switch (i) {
                    case 0:
                        rateType = "";
                        tvIncrease.setText("默认");
                        break;
                    case 1:
                        rateType = "dayrate";
                        tvIncrease.setText("日涨幅");
                        break;
                    case 2:
                        rateType = "weekrate";
                        tvIncrease.setText("周涨幅");
                        break;
                    case 3:
                        rateType = "monthrate";
                        tvIncrease.setText("月涨幅");
                        break;
                    case 4:
                        rateType = "seasonrate";
                        tvIncrease.setText("季度涨幅");
                        break;
                    case 5:
                        rateType = "semesterrate";
                        tvIncrease.setText("半年涨幅");
                        break;
                    case 6:
                        rateType = "yearrate";
                        tvIncrease.setText("一年涨幅");
                        break;
                }
                pageNum = 1;
                presenter.getOptionalFund(rateType, pageNum);
            }
        });
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                presenter.getOptionalFund(rateType, pageNum);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                presenter.getOptionalFund(rateType, pageNum);
            }
        });
        stateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                Log.i("11", "刷新");
            }

            @Override
            public void loginClick() {
                Log.i("11", "登录");
            }
        });


        fundAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<OptionalFund>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, OptionalFund searchFund, int position) {
                Intent intent;
                if (searchFund.getFundType().equals(MiluoConfig.HUOBI) || searchFund.getFundType().equals(MiluoConfig.DUANQI)) {
                    intent = new Intent(mContext, FundCurrencyDetailActivity.class);
                } else {
                    intent = new Intent(mContext, FundDetailActivity.class);
                }
                intent.putExtra("fundId", searchFund.getSellFundId());
                intent.putExtra("fundcode", searchFund.getFundCode());
                ViseLog.i("fundid-->" + searchFund.getSellFundId());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void initView(View view) {
        initInCreasePop();
        initEmptyView();
        initAnimation();
        initialize();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData(true);
    }

    private void initialize() {

        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        fundAdapter = new CollectionAdapter(getActivity(), optionalFunds);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(fundAdapter);
        stateLayout.setUseAnimation(true);

//        stateLayout.setViewSwitchAnimProvider(new FadeScaleViewAnimProvider());

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        if (MyApplication.getInstance().isLogined) {
            increaseAdapter.setCheck(0);
            rateType = "";
            tvIncrease.setText("默认");
            pageNum = 1;
            presenter.getOptionalFund(rateType, pageNum);
//            ViseLog.w("登录了刷新");
        } else {
//            ViseLog.w("没登录刷新");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection;
    }

    @Override
    public void fetchData() {
        if (MyApplication.getInstance().isLogined) {//登录了,查询数据
            pageNum = 1;
            if (presenter == null) {
                presenter = initPresenter();
            }
            presenter.getOptionalFund(rateType, pageNum);
        }
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
    public void onRequestFinished() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }


    @Override
    public void reLogin() {
        Intent intent = new Intent(mContext, QuickLoginActivity.class);
        startActivityForResult(intent, 301);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == 301 && resultCode == Activity.RESULT_OK) {
//            presenter.getOptionalFund(rateType, pageNum);
//        }
    }

    public void scrollToFirst(boolean isSmooth) {
        if (recyclerView == null) {
            return;
        }
        if (isSmooth) {
            recyclerView.smoothScrollToPosition(0);
        } else {
            recyclerView.scrollToPosition(0);
        }
    }


    @Override
    public void onDataSuccess(List<OptionalFund> data) {
        if (pageNum == 1) {
            optionalFunds.clear();
        }
        optionalFunds.addAll(data);
        if (pageNum == 1) {
            scrollToFirst(true);
        }
        if (data.size() < MiluoConfig.DEFAULT_PAGESIZE) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
            pageNum++;
        }
        fundAdapter.notifyDataSetChanged();
        if (optionalFunds.size() == 0) {
            stateLayout.showCustomView(emptyView);
        } else {
            stateLayout.showContentView();
        }
    }

    @Override
    public void initInCreasePop() {
        View popView = View.inflate(mContext, R.layout.pop_win_layout, null);
        lvIncrease = (ListView) popView.findViewById(lsvMore);
        increaseAdapter = new CollectIncreaseAdapter(mContext);
        lvIncrease.setAdapter(increaseAdapter);
        //创建PopupWindow对象，指定宽度和高度
        increaseWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popView.findViewById(R.id.gray_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseWindow.dismiss();
                ivIncrease.startAnimation(downAnimation);
            }
        });
        // 设置动画
        // sortWindow.setAnimationStyle(R.style.popup_window_anim);
        // 设置背景颜色
        // sortWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        // 设置可以获取焦点
        // increaseWindow.setFocusable(true);
        // 设置可以触摸弹出框以外的区域
        increaseWindow.setOutsideTouchable(false);
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
    public void initEmptyView() {
        emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_collect_empty, null);
        emptyView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SearchActivity.class));
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_title_right, R.id.ll_increase})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_title_right:
                startActivity(new Intent(mContext, SearchActivity.class));

                break;
            case R.id.ll_increase:

                if (increaseWindow.isShowing()) {
                    increaseWindow.dismiss();
                } else {
                    ivIncrease.startAnimation(upAnimation);
                    increaseWindow.showAsDropDown(rlOrther);
                }

                break;
        }
    }


    @Override
    public boolean onBackPressed() {

        if (increaseWindow.isShowing()) {
            increaseWindow.dismiss();
            return true;
        }
        return false;

    }
}
