package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.presenter.SettingPresenter;
import com.zhongdi.miluo.ui.activity.login.OpenAccountActivity;
import com.zhongdi.miluo.ui.activity.login.TestActivity;
import com.zhongdi.miluo.ui.activity.login.TestResultActivity;
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
        reFresh();
    }

    private void reFresh() {
        int level = SpCacheUtil.getInstance().getUserTestLevel();

        switch (level) {
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
        if (TextUtils.isEmpty(SpCacheUtil.getInstance().getBankCardCount())) {
            tvBankCardNum.setText("0");
        } else {
            tvBankCardNum.setText(SpCacheUtil.getInstance().getBankCardCount() + "张");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        reFresh();
    }

    @OnClick({R.id.rl_bank_card, R.id.rl_safe_center, R.id.ll_test,R.id.rl_about_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bank_card:

                if (SpCacheUtil.getInstance().getUserFundState() == MiluoConfig.UN_OPEN_ACCOUNT) {
                    Intent intent = new Intent(mContext, OpenAccountActivity.class);
                    intent.putExtra(IntentConfig.SOURCE, IntentConfig.HOME_LOGIN);
                    startActivityForResult(intent, 102);
                } else {
                    startActivity(new Intent(mContext, BankCardListActivity.class));
                }
                break;
            case R.id.rl_safe_center:
                Intent intent = new Intent(mContext, SafeCenterActivity.class);
                startActivityForResult(intent, 101);
                break;
            case R.id.rl_about_us:
                Intent us = new Intent(mContext, AboutUsActivity.class);
                startActivity(us);
                break;
            case R.id.ll_test:
                int level = SpCacheUtil.getInstance().getUserTestLevel();
                if (level > 0) {//已测评 跳转到测评结果页
                    Intent intent_test = new Intent(mContext, TestResultActivity.class);
                    intent_test.putExtra("result", level);
                    intent_test.putExtra(IntentConfig.SOURCE, IntentConfig.SETTING);
                    startActivity(intent_test);
                } else {//为测评到 去测评

                    if (SpCacheUtil.getInstance().getUserFundState() == MiluoConfig.UN_OPEN_ACCOUNT) {
                        showToast("您尚未开户");
                    } else {
                        Intent intent_test = new Intent(mContext, TestActivity.class);
                        intent_test.putExtra(IntentConfig.SOURCE, IntentConfig.SETTING);
                        startActivity(intent_test);
                    }
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 1001) {//退出登录
            setResult(1001);
            finish();
        }

    }
}
