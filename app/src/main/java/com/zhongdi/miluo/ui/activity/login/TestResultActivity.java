package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity2;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.MiluoConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestResultActivity extends BaseActivity2 {
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_retest)
    TextView tvRetest;
    private int riskTestResult;
    private int SOURCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        ButterKnife.bind(this);
        riskTestResult = getIntent().getIntExtra("result", 0);
        SOURCE = getIntent().getIntExtra(IntentConfig.SOURCE, -1);
        initView();
    }

    private void initView() {
        switch (riskTestResult) {
            case MiluoConfig.BAOSHOU:
                ivType.setImageResource(R.drawable.baoshou);
                break;
            case MiluoConfig.WENJIAN:
                ivType.setImageResource(R.drawable.wenjian);
                break;
            case MiluoConfig.CHENGSHU:
                ivType.setImageResource(R.drawable.chengshu);
                break;
            case MiluoConfig.JINQU:
                ivType.setImageResource(R.drawable.jinqu);
                break;
            case MiluoConfig.JIJIN:
                ivType.setImageResource(R.drawable.jijin);
                break;
        }


        switch (SOURCE) {
            case IntentConfig.SETTING://来自设置
                btnSubmit.setText("重新测评");
                tvRetest.setVisibility(View.GONE);
                break;
            case IntentConfig.BUY_FUND://来自申购
                btnSubmit.setText("马上申购");
                tvRetest.setVisibility(View.VISIBLE);
                break;
            default:
                btnSubmit.setText("完成");
                tvRetest.setVisibility(View.VISIBLE);
                break;

        }

    }


    @OnClick({R.id.btn_submit, R.id.tv_retest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (btnSubmit.getText().equals("重新测评")) {
                    Intent intent_test = new Intent(mContext, TestActivity.class);
                    intent_test.putExtra(IntentConfig.SOURCE, IntentConfig.SETTING);
                    startActivity(intent_test);
                    finish();
                } else if (btnSubmit.getText().equals("马上申购")) {
                    setResult(RESULT_OK);
                    finish();

                } else {//完成
//                    if(SOURCE==IntentConfig.TIYANJIN){
//                        Intent intent = new Intent(mContext, MainActivity.class);
//                        intent.putExtra("to", "mine");
//                        startActivity(intent);
//                        finish();
//                    }else {
//                        finish();
//                    }
                    finish();
                }
                break;
            case R.id.tv_retest:
                Intent intent_test = new Intent(mContext, TestActivity.class);
                intent_test.putExtra(IntentConfig.SOURCE, SOURCE);
                startActivity(intent_test);
                finish();
                break;
        }
    }
}
