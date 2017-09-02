package com.zhongdi.miluo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fingdo.statelayout.StateLayout;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.FundAdapter;
import com.zhongdi.miluo.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class FundFragment extends Fragment {
    @BindView(R.id.rv_funds)
    RecyclerView rvFunds;
    Unbinder unbinder;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;

    public static FundFragment newInstance(String info) {
        Bundle args = new Bundle();
        FundFragment fragment = new FundFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fund, null);
        unbinder = ButterKnife.bind(this, view);
        initialize();
//        stateLayout.showErrorView();
        return view;
    }

    private void initialize() {

        List<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("bbb");
        strings.add("vvv");
        strings.add("ccc");
        rvFunds.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        FundAdapter fundAdapter = new FundAdapter(getActivity(), strings);
        rvFunds.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFunds.setAdapter(fundAdapter);
        stateLayout.setUseAnimation(true);
//        stateLayout.setViewSwitchAnimProvider(new FadeScaleViewAnimProvider());
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null &&unbinder != Unbinder.EMPTY){
            unbinder.unbind();
        }
    }
}