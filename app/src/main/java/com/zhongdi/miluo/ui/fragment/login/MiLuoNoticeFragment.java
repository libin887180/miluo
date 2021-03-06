package com.zhongdi.miluo.ui.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.market.MiluoNoticeAdapter;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.eventbus.MessageEvent;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.MessageBean;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.ui.activity.login.NewsDetailActivity;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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

public class MiLuoNoticeFragment extends Fragment {
    Unbinder unbinder;
    View rootView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    MiluoNoticeAdapter adapter;
    List<MessageBean> datas = new ArrayList<>();
    private int pageNum = 1;

    public static MiLuoNoticeFragment newInstance(String info) {
        Bundle args = new Bundle();
        MiLuoNoticeFragment fragment = new MiLuoNoticeFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.layout_refresh_list_wihte, null);
            unbinder = ButterKnife.bind(this, rootView);
            EventBus.getDefault().register(this);
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
        adapter = new MiluoNoticeAdapter(getActivity(), datas);
        adapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<MessageBean>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, MessageBean messageBean, int position) {
                datas.get(position).setStatus("1");
                adapter.notifyDataSetChanged();
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("id", messageBean.getId());
                intent.putExtra(IntentConfig.SOURCE, IntentConfig.FROM_NOTICE);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                getMessages(pageNum);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                getMessages(pageNum);
            }
        });
        stateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                pageNum = 1;
                getMessages(pageNum);
            }

            @Override
            public void loginClick() {

            }
        });
        getMessages(pageNum);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        ViseLog.i("******");
        pageNum=1;
        getMessages(pageNum);
    }

    /**
     * 获取消息列表
     *
     * @param page
     */

    private void getMessages(final int page) {
        Map<String, String> map = new HashMap<>();
        map.put("username", SpCacheUtil.getInstance().getLoginAccount());
        map.put("pageNumber", page + "");
        map.put("type", "1");//2 消息 1公告
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_NEWS, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<MessageBean>>>() {
                    @Override
                    public void onSuccess(MResponse<List<MessageBean>> response, int requestCode) {
                        OnDataSuccess(response.getBody());
                        MyApplication.getInstance().hasNewMsg =false;
                    }

                    @Override
                    public void onFailed(MResponse<List<MessageBean>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        if (response.getCode().equals(ErrorCode.LOGIN_TIME_OUT)) {
                            MyApplication.getInstance().isLogined = false;
                            reLogin();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    private void reLogin() {
        if(MyApplication.getInstance().islogignShow){
            ViseLog.i("登录已显示");
        }else{
            MyApplication.getInstance().islogignShow=true;
            Intent intent  = new Intent(getActivity() ,QuickLoginActivity.class);
            startActivity(intent);
            ViseLog.e("登录未显示");
        }
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 301 && resultCode == Activity.RESULT_OK) {
//            getMessages(pageNum);
//        }
//
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void OnDataSuccess(List<MessageBean> list) {
        if (pageNum == 1) {
            datas.clear();
        }
        if (list.size() < MiluoConfig.DEFAULT_PAGESIZE) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            pageNum++;
            refreshLayout.setEnableLoadmore(true);
        }
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
        datas.addAll(list);
        adapter.notifyDataSetChanged();
        if (datas.size() == 0) {
            stateLayout.showEmptyView();
        }
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
        super.onDestroyView();

    }

}