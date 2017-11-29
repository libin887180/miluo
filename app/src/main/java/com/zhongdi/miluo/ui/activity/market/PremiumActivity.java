package com.zhongdi.miluo.ui.activity.market;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.market.BuyPremiumAdapter;
import com.zhongdi.miluo.adapter.market.OperationPremiumAdapter;
import com.zhongdi.miluo.adapter.market.SellPremiumAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.RateDetail;
import com.zhongdi.miluo.model.RateResponse;
import com.zhongdi.miluo.model.RateTypeDetail;
import com.zhongdi.miluo.presenter.PremiumPresenter;
import com.zhongdi.miluo.view.PremiumView;
import com.zhongdi.miluo.widget.NOScollListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PremiumActivity extends BaseActivity<PremiumPresenter> implements PremiumView {
//    @BindView(R.id.tv_manage_dep_rate)
//    TextView tvManageDepRate;
//    @BindView(R.id.tv_manage_rate)
//    TextView tvManageRate;
//    @BindView(R.id.tv_tuoguan_dep_rate)
//    TextView tvTuoguanDepRate;
//    @BindView(R.id.tv_tuoguan_rate)
//    TextView tvTuoguanRate;
    @BindView(R.id.tv_buy_t1_day)
    TextView tvBuyT1Day;
    @BindView(R.id.tv_buy_check_day)
    TextView tvBuyCheckDay;
    @BindView(R.id.tv_sell_t1_day)
    TextView tvSellT1Day;
    @BindView(R.id.tv_sell_check_day)
    TextView tvSellCheckDay;
    private String sellFundId;

    @BindView(R.id.lv_apply)
    NOScollListView lvApply;
    @BindView(R.id.lv_redeem)
    NOScollListView lvRedeem;
    List<RateDetail> applyRates = new ArrayList<>();
    List<RateDetail> sellRedems = new ArrayList<>();
    private BuyPremiumAdapter applyAdapter;
    private SellPremiumAdapter sellPremiumAdapter;
    private OperationPremiumAdapter operationPremiumAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sellFundId = getIntent().getStringExtra("fundId");
        binding(R.layout.activity_premium_rate);
    }

    @Override
    protected PremiumPresenter initPresenter() {
        return new PremiumPresenter(this);
    }

    @Override
    protected void initialize() {
        presenter.getFundRate(sellFundId);
        applyAdapter = new BuyPremiumAdapter(mContext);
        lvApply.setAdapter(applyAdapter);

        applyAdapter.setDataList(applyRates);


        sellPremiumAdapter = new SellPremiumAdapter(mContext);
        lvRedeem.setAdapter(sellPremiumAdapter);

        sellPremiumAdapter.setDataList(sellRedems);


//        operationPremiumAdapter = new OperationPremiumAdapter(mContext);
//        lvOperations.setAdapter(operationPremiumAdapter);
//        List<String> operations = new ArrayList<>();
//        operations.add("111");
//        operations.add("222");
//        operations.add("333");
//        operations.add("444");
//        operationPremiumAdapter.setDataList(operations);
    }

    @Override
    public void onDateSuccess(RateResponse body) {
//        if (!TextUtils.isEmpty(body.getManageRate())) {
//            tvManageRate.setText(body.getManageRate());
//        }
//        if (!TextUtils.isEmpty(body.getCustodyRate())) {
//            tvTuoguanRate.setText(body.getCustodyRate());
//        }
        List<RateTypeDetail> rateTypes = body.getList();
        if(rateTypes!=null&&rateTypes.size()>0) {
            for (int i = 0; i < rateTypes.size(); i++) {
                if (TextUtils.equals(rateTypes.get(i).getRateType(),"02")) {//前端申购
                    applyRates.addAll(rateTypes.get(i).getData());
                }
                if (TextUtils.equals(rateTypes.get(i).getRateType(),"04")) {//前端申购
                    sellRedems.addAll(rateTypes.get(i).getData());
                }
            }
        }
        applyAdapter.notifyDataSetChanged();
        sellPremiumAdapter.notifyDataSetChanged();

        tvBuyT1Day.setText(body.getPreconfirmdate());
        tvSellT1Day.setText(body.getPreconfirmdate());
        tvBuyCheckDay.setText(body.getPreprofitdate());
        tvSellCheckDay.setText(body.getPreredeemacctdate());
    }
}
