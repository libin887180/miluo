package com.zhongdi.miluo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.CurAssetAdapter;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.HisAssetAdapter;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseFragment;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.HomeAssetBean;
import com.zhongdi.miluo.model.MyProperty;
import com.zhongdi.miluo.presenter.MineFragPresenter;
import com.zhongdi.miluo.ui.activity.login.TiyanjinLoginActivity;
import com.zhongdi.miluo.ui.activity.market.FundCurrencyDetailActivity;
import com.zhongdi.miluo.ui.activity.market.FundDetailActivity;
import com.zhongdi.miluo.ui.activity.mine.GiftListActivity;
import com.zhongdi.miluo.ui.activity.mine.SettingActivity;
import com.zhongdi.miluo.ui.activity.mine.TransactionsActivity;
import com.zhongdi.miluo.ui.activity.mine.TransationsDetailActivity;
import com.zhongdi.miluo.ui.activity.mine.TransationsRecordActivity;
import com.zhongdi.miluo.view.MineFragmentView;
import com.zhongdi.miluo.widget.RiseNumberTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zhongdi.miluo.R.id.recyclerView;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MineFragment extends BaseFragment<MineFragPresenter> implements MineFragmentView {

    List<HomeAssetBean> mDatas = new ArrayList<>();
    List<HomeAssetBean> hismDatas = new ArrayList<>();
    List<Fragment> mFragments;
    List<String> mTitles = new ArrayList<>();
    @BindView(R.id.tv_total_asset)
    RiseNumberTextView tvTotalAsset;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.iv_tiyanjin)
    ImageView ivTiyanjin;
    @BindView(R.id.iv_gift)
    ImageView ivGift;
    Unbinder unbinder;
    @BindView(R.id.cb_visable)
    CheckBox cbVisable;
    @BindView(R.id.tv_yestoday_income)
    RiseNumberTextView tvYestodayIncome;
    @BindView(R.id.tv_total_income)
    RiseNumberTextView tvTotalIncome;
    MyFragmentPagerAdapter adapter;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    private CurAssetAdapter mAdapter;
    private HisAssetAdapter hisAdapter;
    @BindView(recyclerView)
    RecyclerView mRecyclerView;
    LinearLayoutManager layoutManager;
    private int pageIndex = 1;
    private int hisPageIndex = 1;
    private boolean hisCanloadmore;
    private boolean currentCanLoadmore;

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
//        setupViewPager(viewpage);
        cbVisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean cheched) {
                setAssetVisable(cheched);
            }
        });

        mAdapter = new CurAssetAdapter(mContext, mDatas);
        mAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<HomeAssetBean>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, HomeAssetBean assetBean, int position) {
                if (assetBean.getStatus().equals("收益中")) {
                    Intent intent = new Intent(getActivity(), TransationsDetailActivity.class);
                    intent.putExtra("fundcode", assetBean.getFundcode());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), TransationsRecordActivity.class);
                    intent.putExtra("tradeid", assetBean.getTradeid());
                    if (assetBean.getStatus().contains("申购")) {
                        intent.putExtra("tradType", "0");//type (integer): 交易类型0申购，1赎回
                    } else {
                        intent.putExtra("tradType", "1");//type (integer): 交易类型0申购，1赎回
                    }
                    startActivity(intent);
                }

            }
        });
        hisAdapter = new HisAssetAdapter(mContext, hismDatas);

        hisAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<HomeAssetBean>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, HomeAssetBean assetBean, int position) {
                Intent intent;
                if ((assetBean.getFundtype() + "").equals(MiluoConfig.HUOBI)) {
                    intent = new Intent(mContext, FundCurrencyDetailActivity.class);
                } else {
                    intent = new Intent(mContext, FundDetailActivity.class);
                }
                intent.putExtra("fundId", assetBean.getFundid());
                intent.putExtra("fundcode", assetBean.getFundcode());
                startActivity(intent);
            }
        });
//            presenter.getPropertyList(pageIndex, MiluoConfig.DEFAULT_PAGESIZE, 1);
        layoutManager = new LinearLayoutManager(mContext);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext,
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mRecyclerView.setAdapter(hisAdapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    if ((lastVisibleItem == (totalItemCount - 1))) {
//
                        if (tablayout.getSelectedTabPosition() == 0) {
                            if (currentCanLoadmore) {
                                presenter.getPropertyList(pageIndex, MiluoConfig.DEFAULT_PAGESIZE);
                            }
                        } else {
                            if (hisCanloadmore) {
                                presenter.getHisPropertyList(hisPageIndex, MiluoConfig.DEFAULT_PAGESIZE);
                            }
                        }
                    }
                }
            }

        });

        Glide.with(getActivity()).asGif().load(R.drawable.gift).into(ivGift);
        Glide.with(getActivity()).asGif().load(R.drawable.tiyanjin).into(ivTiyanjin);


    }

    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
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
            pageIndex = 1;
            hisPageIndex = 1;
            presenter.getPropertyList(pageIndex, MiluoConfig.DEFAULT_PAGESIZE);
            presenter.getHisPropertyList(hisPageIndex, MiluoConfig.DEFAULT_PAGESIZE);
//            CurAssetFragment childAt = (CurAssetFragment) mFragments.get(viewpage.getCurrentItem());
//            childAt.fetchData();
        }
    }


    @Override
    public void onDataSuccess(MyProperty property) {
        // 设置数据
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
    public void onCurrentPropertyList(List<HomeAssetBean> assetList) {
        if (pageIndex == 1) {
            mDatas.clear();
        }

        if (assetList.size() < MiluoConfig.DEFAULT_PAGESIZE) {
            currentCanLoadmore = false;
        } else {
            currentCanLoadmore = true;
            pageIndex++;
        }
        mDatas.addAll(assetList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHisPropertyList(List<HomeAssetBean> hisassetList) {
        if (hisPageIndex == 1) {
            hismDatas.clear();
        }
        if (hisassetList.size() < MiluoConfig.DEFAULT_PAGESIZE) {
            hisCanloadmore = false;
        } else {
            hisCanloadmore = true;
            hisPageIndex++;
        }
        hismDatas.addAll(hisassetList);
        hisAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.tv_total_asset, R.id.tv_title_left, R.id.tv_title_right,R.id.ll_tiyanjin,R.id.ll_gift})
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
            case R.id.ll_tiyanjin:
                startActivity(new Intent(mContext, TiyanjinLoginActivity.class));
                break;
            case R.id.ll_gift:
                startActivity(new Intent(mContext, GiftListActivity.class));
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}