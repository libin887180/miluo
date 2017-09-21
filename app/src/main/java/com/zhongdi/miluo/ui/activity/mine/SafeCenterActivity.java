package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.presenter.SafeCenterPresenter;
import com.zhongdi.miluo.view.SafeCenterView;
import com.zhongdi.miluo.widget.AlertDialog;

import butterknife.OnClick;

public class SafeCenterActivity extends BaseActivity<SafeCenterPresenter> implements SafeCenterView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_safe_center);
    }

    @Override
    protected SafeCenterPresenter initPresenter() {
        return new SafeCenterPresenter(this);
    }

    @Override
    protected void initialize() {

    }

    @OnClick({R.id.rl_login_psw, R.id.rl_modify_deal_psw, R.id.rl_modify_tel, R.id.rl_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_login_psw:
                Intent intent = new Intent(mContext, SendCodeActivity.class);
                intent.putExtra(IntentConfig.SOURCE, IntentConfig.FROM_MODIFY_PSW);
                startActivity(intent);
                break;
            case R.id.rl_modify_deal_psw:
                Intent intent_deal = new Intent(mContext, SendCodeActivity.class);
                intent_deal.putExtra(IntentConfig.SOURCE, IntentConfig.FROM_MODIFY_DEAL_PSW);
                startActivity(intent_deal);
                break;
            case R.id.rl_modify_tel:
                startActivity(new Intent(mContext, CheckDealPswActivity.class));
                break;
            case R.id.rl_quit:
                showQuitDialog();
                break;
        }
    }

    @Override
    public void showQuitDialog() {
        new AlertDialog(mContext).builder().setMsg("确定不是手滑了？")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).setPositiveButton("退出", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).show();
    }
}
