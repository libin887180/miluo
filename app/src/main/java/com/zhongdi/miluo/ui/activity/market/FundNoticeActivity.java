package com.zhongdi.miluo.ui.activity.market;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.FundNoticeAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.FundNoticePresenter;
import com.zhongdi.miluo.view.FundNoticeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FundNoticeActivity extends BaseActivity<FundNoticePresenter> implements FundNoticeView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private FundNoticeAdapter noticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_fund_notice);
    }

    @Override
    protected FundNoticePresenter initPresenter() {
        return new FundNoticePresenter(this);
    }

    @Override
    protected void initialize() {
        List<String> datas = new ArrayList<>();
        datas.add("1");
        datas.add("11");
        datas.add("111");
        datas.add("1111");
        datas.add("1111");
        datas.add("1111");
        datas.add("1111");
        datas.add("1111");
        noticeAdapter = new FundNoticeAdapter(mContext, datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(noticeAdapter);
    }

}
