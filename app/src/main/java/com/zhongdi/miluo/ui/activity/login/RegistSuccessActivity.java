package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.presenter.RegistSuccessPresenter;
import com.zhongdi.miluo.view.RegistSuccessView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegistSuccessActivity extends BaseActivity<RegistSuccessPresenter> implements RegistSuccessView {

    //    @BindView(R.id.as_status)
//    CustomStatusView asStatus;
    @BindView(R.id.as_status)
    ImageView asStatus;
    @BindView(R.id.tv_tyj)
    TextView tvTyj;
    private int source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        source = getIntent().getIntExtra(IntentConfig.SOURCE, -1);
        binding(R.layout.activity_regist_success);
    }

    @Override
    protected RegistSuccessPresenter initPresenter() {
        return new RegistSuccessPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        asStatus.loadLoading();
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                asStatus.loadSuccess();
//            }
//        }, 1 * 1000);

    }

    @Override
    protected void initialize() {


    }

    @OnClick({R.id.btn_open, R.id.btn_main, R.id.tv_tyj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                Intent intent = new Intent(mContext, OpenAccountActivity.class);
                intent.putExtra(IntentConfig.SOURCE, IntentConfig.HOME_LOGIN);
                startActivity(intent);
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btn_main:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.tv_tyj:
                Intent tyj = new Intent(mContext, TiyanjinInfoActivity.class);
                startActivity(tyj);
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
