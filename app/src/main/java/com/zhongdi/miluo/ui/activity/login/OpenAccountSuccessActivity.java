package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.presenter.OpenAccountSuccessPresenter;
import com.zhongdi.miluo.view.OpenAccountSuccessView;
import com.zhongdi.miluo.widget.CustomStatusView;

import butterknife.BindView;
import butterknife.OnClick;

public class OpenAccountSuccessActivity extends BaseActivity<OpenAccountSuccessPresenter> implements OpenAccountSuccessView {

    @BindView(R.id.as_status)
    CustomStatusView asStatus;
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.btn_main)
    Button btnMain;
    private int source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        source = getIntent().getIntExtra(IntentConfig.SOURCE, -1);
        binding(R.layout.activity_openaccount_success);
    }

    @Override
    protected OpenAccountSuccessPresenter initPresenter() {
        return new OpenAccountSuccessPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        asStatus.loadLoading();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                asStatus.loadSuccess();
            }
        }, 2 * 1000);

    }

    @Override
    protected void initialize() {
        if (SpCacheUtil.getInstance().getUserTestLevel() > 0) {//不用风险评测
            btnTest.setText("完成");
            btnMain.setVisibility(View.GONE);
        } else {
            btnTest.setText("风险评测");
            btnMain.setVisibility(View.VISIBLE);
        }


    }

    @OnClick({R.id.btn_test, R.id.btn_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test:
                Intent intent =  new Intent(mContext, TestActivity.class);
                intent.putExtra(IntentConfig.SOURCE,source);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_main:
                finish();
                break;
        }
    }
}
