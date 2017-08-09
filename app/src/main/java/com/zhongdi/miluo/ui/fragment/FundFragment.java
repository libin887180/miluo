package com.zhongdi.miluo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.FundAdapter;

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
        View view = inflater.inflate(R.layout.fragment_fund1, null);
        unbinder = ButterKnife.bind(this, view);
        List<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("bbb");
        strings.add("vvv");
        strings.add("ccc");
        FundAdapter fundAdapter = new FundAdapter(getActivity(), strings);
        rvFunds.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFunds.setAdapter(fundAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}