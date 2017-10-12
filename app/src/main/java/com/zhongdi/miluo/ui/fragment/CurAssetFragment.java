package com.zhongdi.miluo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BaseRecyclerAdapter;
import com.zhongdi.miluo.adapter.CurAssetAdapter;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.HisAssetAdapter;
import com.zhongdi.miluo.base.BaseFragment;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.HomeAssetBean;
import com.zhongdi.miluo.presenter.AssetFragmentPresenter;
import com.zhongdi.miluo.ui.activity.mine.TransationsDetailActivity;
import com.zhongdi.miluo.view.AssetFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ explain:
 * @ author：libin
 * @ email：libin887180@163.com
 */
public class CurAssetFragment extends BaseFragment<AssetFragmentPresenter> implements AssetFragmentView {
    private String title = "";
    private static final String KEY = "title";
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    Unbinder unbinder;
    List<HomeAssetBean> mDatas = new ArrayList<>();
    private BaseRecyclerAdapter mAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private int pageIndex = 1;

    public static CurAssetFragment newInstance(String title) {
        CurAssetFragment fragment = new CurAssetFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected AssetFragmentPresenter initPresenter() {
        return new AssetFragmentPresenter(this);
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
    protected void initView(View view) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            title = arguments.getString(KEY);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext,
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);


        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableOverScroll(false);

        //0当前资产1历史资产
        if (title.equals("当前资产")) {
            mAdapter = new CurAssetAdapter(mContext, mDatas);
            mAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<HomeAssetBean>() {
                @Override
                public void onClick(View view, RecyclerView.ViewHolder holder, HomeAssetBean assetBean, int position) {
                    Intent intent = new Intent(getActivity(), TransationsDetailActivity.class);
                    intent.putExtra("fundcode", assetBean.getFundcode());
                    startActivity(intent);

                }
            });
            presenter.getPropertyList(pageIndex, MiluoConfig.DEFAULT_PAGESIZE, 0);


        } else {
            mAdapter = new HisAssetAdapter(mContext, mDatas);
            mAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                    startActivity(new Intent(getActivity(), TransationsDetailActivity.class));
                }
            });
            presenter.getPropertyList(pageIndex, MiluoConfig.DEFAULT_PAGESIZE, 1);
        }
        mRecyclerView.setAdapter(mAdapter);
    }


    public void scrollToFirst(boolean isSmooth) {
        if (mRecyclerView == null) {
            return;
        }
        if (isSmooth) {
            mRecyclerView.smoothScrollToPosition(0);
        } else {
            mRecyclerView.scrollToPosition(0);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onDataSuccess(List<HomeAssetBean> assetList) {

        if (pageIndex == 1) {
            mDatas.clear();
        }
        mDatas.addAll(assetList);

        if (assetList.size() < MiluoConfig.DEFAULT_PAGESIZE) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
            pageIndex++;
        }
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
        mAdapter.notifyDataSetChanged();
        if (mDatas.size() == 0) {
            stateLayout.showEmptyView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
