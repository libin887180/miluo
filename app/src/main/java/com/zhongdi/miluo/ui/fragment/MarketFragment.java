package com.zhongdi.miluo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseFragment;
import com.zhongdi.miluo.presenter.MarketPresenter;
import com.zhongdi.miluo.view.MarketView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MarketFragment extends BaseFragment<MarketPresenter> implements MarketView {
    @BindView(R.id.tvInfo)
    TextView tvInfo;
    Unbinder unbinder;
private  View rootView;
    public static MarketFragment newInstance(String info) {
        Bundle args = new Bundle();
        MarketFragment fragment = new MarketFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MarketPresenter initPresenter() {
        return new MarketPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_demo, container, false);
            binding();
            unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void doSomething() {
        tvInfo.setText("asdasdasddsfwewf");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tvInfo)
    public void onViewClicked() {
        presenter.isEmailValid("kenn@163.com");
    }
}