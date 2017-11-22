package com.zhongdi.miluo.ui.fragment.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.market.DealingAdapter;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.DealRecord;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.ui.activity.mine.TransationsRecordActivity;
import com.zhongdi.miluo.widget.RecycleViewDivider;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class DealingFragment extends Fragment {
    Unbinder unbinder;
    View rootView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    DealingAdapter adapter;
    List<DealRecord> dealRecords = new ArrayList<>();
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private int pageIndex = 1;

    public static DealingFragment newInstance(String info) {
        Bundle args = new Bundle();
        DealingFragment fragment = new DealingFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.layout_refresh_list, null);
            unbinder = ButterKnife.bind(this, rootView);
            initialize();
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

    private void initialize() {
        adapter = new DealingAdapter(getActivity(), dealRecords);
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, 25, getResources().getColor(R.color.grey_bg)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<DealRecord>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, DealRecord dealRecord, int position) {
                if (dealRecord.getTypeitem().equals("申购") || dealRecord.getTypeitem().equals("赎回")) {
                    Intent intent = new Intent(getActivity(), TransationsRecordActivity.class);
                    intent.putExtra("tradeid", dealRecord.getTradeid() + "");
                    if (dealRecord.getTypeitem().equals("申购")) {
                        intent.putExtra("tradType", "0");//type (integer): 交易类型0申购，1赎回
                    } else {
                        intent.putExtra("tradType", "1");//type (integer): 交易类型0申购，1赎回
                    }
                    startActivity(intent);
                }
            }
        });
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pageIndex = 1;
                getDealingRecords(pageIndex, MiluoConfig.DEFAULT_PAGESIZE);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                getDealingRecords(pageIndex, MiluoConfig.DEFAULT_PAGESIZE);
            }
        });
        getDealingRecords(pageIndex, MiluoConfig.DEFAULT_PAGESIZE);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
        super.onDestroyView();

    }

    /**
     * 获取交易记录
     *
     * @param pageindex 页数
     * @param pageSize  每页条数
     */
    private void getDealingRecords(final int pageindex, int pageSize) {
        Map<String, String> map = new HashMap<>();
        map.put("pageIndex", pageindex + "");
        map.put("pageSize", pageSize + "");
        map.put("type", "1");//1 进行中2 已完成
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.TRADE_RECORD_LIST, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<DealRecord>>>() {
                    @Override
                    public void onSuccess(MResponse<List<DealRecord>> response, int requestCode) {
                        if (pageIndex == 1) {
                            dealRecords.clear();
                        }
                        if (response.getBody().size() < MiluoConfig.DEFAULT_PAGESIZE) {
                            refreshLayout.setEnableLoadmore(false);
                        } else {
                            refreshLayout.setEnableLoadmore(true);
                            pageIndex++;
                        }
                        dealRecords.addAll(response.getBody());

                        adapter.notifyDataSetChanged();
                        if (dealRecords.size() == 0) {
                            stateLayout.showEmptyView();
                        } else {
                            stateLayout.showContentView();
                        }
                    }

                    @Override
                    public void onFailed(MResponse<List<DealRecord>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        if (response.getCode().equals(ErrorCode.LOGIN_TIME_OUT)) {
                        reLogin();
                        } else {
                            Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {
                        refreshLayout.finishLoadmore();
                        refreshLayout.finishRefreshing();
                    }
                });
    }

    private void reLogin() {
        Intent intent  = new Intent(getActivity(), QuickLoginActivity.class);
        startActivityForResult(intent, 301);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 301 && resultCode == Activity.RESULT_OK) {
            getDealingRecords(pageIndex, MiluoConfig.DEFAULT_PAGESIZE);
        }
    }
}