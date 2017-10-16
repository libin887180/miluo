package com.zhongdi.miluo.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.TradeStepAdapter;
import com.zhongdi.miluo.adapter.mine.TransInfoAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.TradeRecord;
import com.zhongdi.miluo.model.TradeRecord.Part2Bean.StepsBean;
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
    @BindView(R.id.tv_trade_name)
    TextView tvTradeName;
    @BindView(R.id.tv_reqtime)
    TextView tvReqtime;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.iv_steps)
    ImageView ivSteps;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_steps)
    LinearLayout llSteps;
    TradeStepAdapter stepAdapter;
    private String tradeid;
    private String tradeType;
    List<StepsBean> tradeSteps = new ArrayList<>();
    List<TradeRecord.Part3Bean> transInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tradeid = getIntent().getStringExtra("tradeid");
        tradeType = getIntent().getStringExtra("tradType");
        binding(R.layout.activity_transaction_record);
    }

    @Override
    protected TransactionRecordPresenter initPresenter() {
        return new TransactionRecordPresenter(this);
    }

    @Override
    protected void initialize() {

        presenter.getTransRecord(tradeid, tradeType);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        transAdapter = new TransInfoAdapter(mContext);
        transAdapter.setDataList(transInfo);
        listview.setFocusable(false);
        listview.setAdapter(transAdapter);
        showToast(tradeid);
    }

    @Override
    public void OnDataSuccess(TradeRecord body) {


        tradeSteps.clear();
        tradeSteps.addAll(body.getPart2().getSteps());
        String cancelstatus = body.getPart1().getCancelstatus();
        String currentStep = body.getPart2().getCurrentStep();
        String title = body.getPart1().getTitle();

        tvTradeName.setText(title);
        tvReqtime.setText(body.getPart1().getReqtime());
        tvAmount.setText(body.getPart1().getAmount() + "");
        if (body.getPart2().getSteps() != null && body.getPart2().getSteps().size() > 0) {
            llEmpty.setVisibility(View.GONE);
            llSteps.setVisibility(View.VISIBLE);
            if (body.getPart2().getSteps().size() == 3) {
                switch (currentStep) {
                    case "1":
                        ivSteps.setImageResource(R.drawable.step3_1);
                        break;
                    case "2":
                        ivSteps.setImageResource(R.drawable.step3_2);
                        break;
                    case "3":
                        ivSteps.setImageResource(R.drawable.step3_3);
                        break;
                }
            } else {
                ivSteps.setImageResource(R.drawable.step2_2);
            }

        } else {
            llEmpty.setVisibility(View.VISIBLE);
            llSteps.setVisibility(View.GONE);
        }
        stepAdapter = new TradeStepAdapter(mContext, tradeSteps, cancelstatus, Integer.parseInt(currentStep), title);
        recyclerView.setAdapter(stepAdapter);

        if (body.getPart3() != null && body.getPart3().size() > 0) {
            transInfo.addAll(body.getPart3() );
        }
        transAdapter.notifyDataSetChanged();

    }
}
