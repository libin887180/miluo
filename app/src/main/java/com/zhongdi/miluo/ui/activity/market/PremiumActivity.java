package com.zhongdi.miluo.ui.activity.market;

import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.market.BuyPremiumAdapter;
import com.zhongdi.miluo.adapter.market.OperationPremiumAdapter;
import com.zhongdi.miluo.adapter.market.SellPremiumAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.PremiumPresenter;
import com.zhongdi.miluo.view.PremiumView;
import com.zhongdi.miluo.widget.NOScollListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PremiumActivity extends BaseActivity<PremiumPresenter> implements PremiumView {


    @BindView(R.id.lv_apply)
    NOScollListView lvApply;
    @BindView(R.id.lv_redeem)
    NOScollListView lvRedeem;
    @BindView(R.id.lv_operations)
    NOScollListView lvOperations;


    private BuyPremiumAdapter premiumAdapter;
    private SellPremiumAdapter sellPremiumAdapter;
    private OperationPremiumAdapter operationPremiumAdapter;
    List<String> strings = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_premium_rate);
    }

    @Override
    protected PremiumPresenter initPresenter() {
        return new PremiumPresenter(this);
    }

    @Override
    protected void initialize() {

        premiumAdapter = new BuyPremiumAdapter(mContext);
        lvApply.setAdapter(premiumAdapter);
        List<String> strings = new ArrayList<>();
        strings.add("111");
        strings.add("222");
        strings.add("333");
        strings.add("444");
        premiumAdapter.setDataList(strings);


        sellPremiumAdapter = new SellPremiumAdapter(mContext);
        lvRedeem.setAdapter(sellPremiumAdapter);
        List<String> redems = new ArrayList<>();
        redems.add("111");
        redems.add("222");
        redems.add("333");
        redems.add("444");
        sellPremiumAdapter.setDataList(redems);


        operationPremiumAdapter = new OperationPremiumAdapter(mContext);
        lvOperations.setAdapter(operationPremiumAdapter);
        List<String> operations = new ArrayList<>();
        operations.add("111");
        operations.add("222");
        operations.add("333");
        operations.add("444");
        operationPremiumAdapter.setDataList(operations);
    }

}
