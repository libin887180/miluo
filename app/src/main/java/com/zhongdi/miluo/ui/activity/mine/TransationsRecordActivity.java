package com.zhongdi.miluo.ui.activity.mine;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.mine.TransInfoAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.TransactionRecordPresenter;
import com.zhongdi.miluo.view.TransactionRecordView;
import com.zhongdi.miluo.widget.NOScollListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TransationsRecordActivity extends BaseActivity<TransactionRecordPresenter> implements TransactionRecordView {

    @BindView(R.id.listview)
    NOScollListView listview;
    TransInfoAdapter transAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_transaction_record);
    }

    @Override
    protected TransactionRecordPresenter initPresenter() {
        return new TransactionRecordPresenter(this);
    }

    @Override
    protected void initialize() {
        List<String> datas = new ArrayList<>();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        transAdapter = new TransInfoAdapter(mContext);
        transAdapter.setDataList(datas);
        listview.setFocusable(false);
        listview.setAdapter(transAdapter);
    }
}
