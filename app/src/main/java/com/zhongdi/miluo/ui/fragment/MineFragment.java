package com.zhongdi.miluo.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.CurAssetAdapter2;
import com.zhongdi.miluo.adapter.HisAssetAdapter2;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseFragment;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.HomeAssetBean;
import com.zhongdi.miluo.model.MyProperty;
import com.zhongdi.miluo.presenter.MineFragPresenter;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.ui.activity.login.TiyanjinInfoActivity;
import com.zhongdi.miluo.ui.activity.market.FundCurrencyDetailActivity;
import com.zhongdi.miluo.ui.activity.market.FundDetailActivity;
import com.zhongdi.miluo.ui.activity.mine.GiftListActivity;
import com.zhongdi.miluo.ui.activity.mine.SettingActivity;
import com.zhongdi.miluo.ui.activity.mine.TiyanjinTransDetailActivity;
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
    //    private CurAssetAdapter mAdapter;
//    private HisAssetAdapter hisAdapter;
    private CurAssetAdapter2 mAdapter;
    private HisAssetAdapter2 hisAdapter;
    //    @BindView(R.id.recyclerView)
//    RecyclerView mRecyclerView;
    @BindView(R.id.listview)
    ListView listView;
    LinearLayoutManager layoutManager;
    View emptyView;
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
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void fetchData() {
        refreshData();
    }

    @Override
    protected void initView(View view) {
//        setupViewPager(viewpage);

        cbVisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean cheched) {
                MyApplication.getInstance().assetVisable = cheched;
                setAssetVisable(cheched);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }
        mAdapter = new CurAssetAdapter2(mContext, mDatas);
        listView.setOnItemClickListener(currentOnclickListener);
        hisAdapter = new HisAssetAdapter2(mContext, hismDatas);
        listView.setAdapter(mAdapter);
        emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty, null);
//         emptyView = view.findViewById(R.id.emptyview);
//            presenter.getPropertyList(pageIndex, MiluoConfig.DEFAULT_PAGESIZE, 1);
        layoutManager = new LinearLayoutManager(mContext);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    listView.setAdapter(mAdapter);
                    listView.setOnItemClickListener(currentOnclickListener);
                    if (mDatas.size() == 0) {
                        if (listView.getHeaderViewsCount() == 0) {
                            listView.addHeaderView(emptyView);
                        }
                    } else {
                        listView.removeHeaderView(emptyView);
                    }
                } else {
                    listView.setAdapter(hisAdapter);
                    listView.setOnItemClickListener(hisOnclickListener);
                    if (hismDatas.size() == 0) {
                        if (listView.getHeaderViewsCount() == 0) {
                            listView.addHeaderView(emptyView);
                        }
                    } else {
                        listView.removeHeaderView(emptyView);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = listView.getLastVisiblePosition() ;
                    int totalItemCount = listView.getCount();
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

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }


        });
    }

    AdapterView.OnItemClickListener currentOnclickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mDatas.size() > 0) {
                HomeAssetBean assetBean = mDatas.get(position);

                if (TextUtils.equals(assetBean.getStatus(),"收益中")) {
                    Intent intent = new Intent(getActivity(), TransationsDetailActivity.class);
                    intent.putExtra("fundcode", assetBean.getFundcode());
                    startActivityForResult(intent, 101);
                } else {
                    Intent intent = new Intent(getActivity(), TransationsRecordActivity.class);
                    intent.putExtra("tradeid", assetBean.getTradeid());
                    if (assetBean.getStatus().contains("申购")) {
                        intent.putExtra("tradType", "0");//type (integer): 交易类型0申购，1赎回
                    } else {
                        intent.putExtra("tradType", "1");//type (integer): 交易类型0申购，1赎回
                    }
                    startActivityForResult(intent, 101);
                }
            }
        }
    };
    AdapterView.OnItemClickListener hisOnclickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (hismDatas.size() > 0) {
                HomeAssetBean assetBean = hismDatas.get(position);
                Intent intent;
                if ((assetBean.getFundtype() + "").equals(MiluoConfig.HUOBI) || (assetBean.getFundtype() + "").equals(MiluoConfig.DUANQI)) {
                    intent = new Intent(mContext, FundCurrencyDetailActivity.class);
                } else {
                    intent = new Intent(mContext, FundDetailActivity.class);
                }
                intent.putExtra("fundId", assetBean.getFundid());
                intent.putExtra("fundcode", assetBean.getFundcode());
                startActivity(intent);
            }
        }
    };

    private void setAssetVisable(boolean visable) {
        tvTotalAsset.setNumVisable(visable);
        tvYestodayIncome.setNumVisable(visable);
        tvTotalIncome.setNumVisable(visable);
        mAdapter.setAssetVisable(visable);
        hisAdapter.setAssetVisable(visable);
        mAdapter.notifyDataSetChanged();
        hisAdapter.notifyDataSetChanged();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData(true);
    }

    public void refreshData() {
        if (MyApplication.getInstance().isLogined) {//登录了,查询数据
            if (presenter == null) {
                presenter = initPresenter();
            }
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
        cbVisable.setChecked(MyApplication.getInstance().assetVisable);
        tvTotalAsset.withNumber(Double.parseDouble(property.getTotalasset()));
        tvYestodayIncome.withNumber(Double.parseDouble(property.getDayincome()));
        tvTotalIncome.withNumber(Double.parseDouble(property.getAccumulatedincome()));
        tvTotalIncome.setDuration(1000);
        if (MyApplication.getInstance().assetVisable) {
            // 设置数据

            // 设置动画播放时间
            tvTotalAsset.setDuration(1000);
            // 开始播放动画
            tvTotalAsset.start();

            tvYestodayIncome.setDuration(1000);
            // 开始播放动画
            tvYestodayIncome.start();

            // 开始播放动画
            tvTotalIncome.start();
        } else {
            setAssetVisable(MyApplication.getInstance().assetVisable);
        }
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
        if (tablayout.getSelectedTabPosition() == 0) {

            if (mDatas.size() == 0) {
                if (listView.getHeaderViewsCount() == 0) {
                    listView.addHeaderView(emptyView);
                }
            } else {
                listView.removeHeaderView(emptyView);
            }
        }

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
        if (tablayout.getSelectedTabPosition() == 1) {
            if (hismDatas.size() == 0) {
                if (listView.getHeaderViewsCount() == 0) {
                    listView.addHeaderView(emptyView);
                }
            } else {
                listView.removeHeaderView(emptyView);
            }
        }
    }

    @Override
    public void onRequestFinished() {
    }

    @Override
    public void reLogin() {
        Intent intent = new Intent(mContext, QuickLoginActivity.class);
        startActivityForResult(intent, 301);
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

    @OnClick({R.id.tv_total_asset, R.id.tv_title_left, R.id.tv_title_right, R.id.ll_tiyanjin, R.id.ll_gift})
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
                if (TextUtils.equals(SpCacheUtil.getInstance().getActivityStatus(),"0")) {
                    Intent tiyanjin_info = new Intent(mContext, TiyanjinInfoActivity.class);
                    startActivity(tiyanjin_info);
                } else {
                    startActivity(new Intent(mContext, TiyanjinTransDetailActivity.class));
                }
                break;
            case R.id.ll_gift:
                startActivity(new Intent(mContext, GiftListActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 1001) {
            refreshData();
        }
//        if (requestCode == 301 && resultCode == Activity.RESULT_OK) {
//            refreshData();
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}