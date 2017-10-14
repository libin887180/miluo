package com.zhongdi.miluo.ui.fragment.login;

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
import com.zhongdi.miluo.adapter.market.BeginnerInfoAdapter;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.InfomationNote;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;

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

public class BeginnerFragment extends Fragment {
    Unbinder unbinder;
    View rootView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    BeginnerInfoAdapter adapter;
    List<InfomationNote> notes = new ArrayList<>();
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private int pageNumber = 1;
    private String ARTICLETAG = "08";// 08新手秘籍
    public static BeginnerFragment newInstance(String info) {
        Bundle args = new Bundle();
        BeginnerFragment fragment = new BeginnerFragment();
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
        adapter = new BeginnerInfoAdapter(getActivity(), notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
            }
        });
        getFundEssay(ARTICLETAG, pageNumber);
        stateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                pageNumber =1;
                getFundEssay(ARTICLETAG, pageNumber);
            }

            @Override
            public void loginClick() {

            }
        });
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pageNumber =1;
                getFundEssay(ARTICLETAG, pageNumber);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                getFundEssay(ARTICLETAG, pageNumber);
            }
        });
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
        super.onDestroyView();

    }

    /**
     * @param articletag 01首页推荐 02利得原创 03基金资讯 04基金研报 05基金导读 06基金观点 07理财热点 08新手秘籍
     *                   09其他 10要问(推荐,资讯,基金导读,理财热点) 11投研(原创,研报,基金观点)
     * @param pageNumber 页码
     */
    public void getFundEssay(String articletag, int pageNumber) {
        Map<String, String> map = new HashMap<>();
        map.put("articletag", articletag);
        map.put("pageNumber", pageNumber + "");
        map.put("pageSize", MiluoConfig.DEFAULT_PAGESIZE + "");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_ESSAY, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<InfomationNote>>>() {
                    @Override
                    public void onSuccess(MResponse<List<InfomationNote>> response, int requestCode) {

                        onDataSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<List<InfomationNote>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    private void onDataSuccess(List<InfomationNote> body) {

        if (pageNumber == 1) {
            notes.clear();
        }
        notes.addAll(body);

        if(body.size()<MiluoConfig.DEFAULT_PAGESIZE){
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
            pageNumber++;
        }

        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
        adapter.notifyDataSetChanged();
        if(notes.size()==0){
            stateLayout.showEmptyView();
        }

    }
}