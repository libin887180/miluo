package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.presenter.SettingPresenter;
import com.zhongdi.miluo.ui.activity.login.TestActivity;
import com.zhongdi.miluo.view.SettingView;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingView {

    @BindView(R.id.tv_risk_level)
    TextView tvRiskLevel;
    @BindView(R.id.tv_bank_card_num)
    TextView tvBankCardNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_setting);
    }

    @Override
    protected SettingPresenter initPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected void initialize() {
        int level = SpCacheUtil.getInstance().getUserTestLevel();

        switch (level){
            case MiluoConfig.BAOSHOU:
                tvRiskLevel.setText("保守型");
                break;
            case MiluoConfig.WENJIAN:
                tvRiskLevel.setText("稳健型");
                break;
            case MiluoConfig.CHENGSHU:
                tvRiskLevel.setText("成熟型");
                break;
            case MiluoConfig.JINQU:
                tvRiskLevel.setText("进取型");
                break;
            case MiluoConfig.JIJIN:
                tvRiskLevel.setText("激进型");
                break;
            default:
                tvRiskLevel.setText("未测评");
                break;
        }
        tvBankCardNum.setText(SpCacheUtil.getInstance().getBankCardCount()+"");
    }

    @OnClick({R.id.rl_bank_card, R.id.rl_safe_center, R.id.ll_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bank_card:
                startActivity(new Intent(mContext, BankCardListActivity.class));
                break;
            case R.id.rl_safe_center:
                startActivity(new Intent(mContext, SafeCenterActivity.class));
                break;
            case R.id.ll_test:
                startActivity(new Intent(mContext, TestActivity.class));
                break;
        }
    }
}
