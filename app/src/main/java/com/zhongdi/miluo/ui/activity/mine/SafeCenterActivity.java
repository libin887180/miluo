package com.zhongdi.miluo.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.presenter.SafeCenterPresenter;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
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
                if (TextUtils.isEmpty(SpCacheUtil.getInstance().getUserPwd())) {//没有设置密码
                    Intent intent_forget = new Intent(mContext, SendCodeActivity.class);
                    intent_forget.putExtra(IntentConfig.SOURCE, IntentConfig.FROM_SET_LOGIN_PSW);
                    startActivity(intent_forget);
                } else {//已设置密码  修改密码
                    Intent intent = new Intent(mContext, SendCodeActivity.class);
                    intent.putExtra(IntentConfig.SOURCE, IntentConfig.FROM_MODIFY_PSW);
                    startActivity(intent);
                }
                break;
            case R.id.rl_modify_deal_psw:
                if (SpCacheUtil.getInstance().getUserFundState() == MiluoConfig.UN_OPEN_ACCOUNT) {
                    showToast("您尚未设置交易密码");
                } else {
                    Intent intent_deal = new Intent(mContext, SendCodeActivity.class);
                    intent_deal.putExtra(IntentConfig.SOURCE, IntentConfig.FROM_MODIFY_DEAL_PSW);
                    startActivity(intent_deal);
                }
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
                presenter.loginOut();
            }
        }).show();
    }

    @Override
    public void onSuccess() {
        SpCacheUtil.getInstance().clearUserInfo();
        MyApplication.getInstance().isLogined = false;
        setResult(1001);
        finish();

    }

    @Override
    public void reLogin() {
        Intent intent  = new Intent(mContext, QuickLoginActivity.class);
        startActivityForResult(intent, 301);
    }
}
