package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.BankListAdapter;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.BankInfo;
import com.zhongdi.miluo.presenter.ChooseBankPresenter;
import com.zhongdi.miluo.view.ChooseBankView;
import com.zhongdi.miluo.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChooseBankActivity extends BaseActivity<ChooseBankPresenter> implements ChooseBankView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    BankListAdapter bankAdapter;
    @BindView(R.id.title)
    TextView title;
    List<BankInfo> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_recycle_list);
    }

    @Override
    protected ChooseBankPresenter initPresenter() {
        return new ChooseBankPresenter(this);
    }

    @Override
    protected void initialize() {
        title.setText("选择银行");
        bankAdapter = new BankListAdapter(mContext, datas);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(bankAdapter);
        bankAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<BankInfo>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, BankInfo bankInfo, int position) {
                bankAdapter.setCheck(position);
                Intent intent = new Intent();
                intent.putExtra("bankno",bankInfo.getBankno());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        initData();
    }

    private void initData() {
        presenter.getBankList();
    }

    @Override
    public void onSuccess(List<BankInfo> bankList) {
        datas.addAll(bankList);
        bankAdapter.notifyDataSetChanged();
    }

}
