package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.RegistSuccessPresenter;
import com.zhongdi.miluo.view.RegistSuccessView;
import com.zhongdi.miluo.widget.CustomStatusView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegistSuccessActivity extends BaseActivity<RegistSuccessPresenter> implements RegistSuccessView {

    @BindView(R.id.as_status)
    CustomStatusView asStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_regist_success);
    }

    @Override
    protected RegistSuccessPresenter initPresenter() {
        return new RegistSuccessPresenter(this);
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



    }

    @OnClick({R.id.btn_open, R.id.btn_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                startActivity(new Intent(mContext,OpenAccountActivity.class));
                finish();
                break;
            case R.id.btn_main:
                break;
        }
    }
}
