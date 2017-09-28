package com.zhongdi.miluo.ui.activity.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity2;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.model.RiskTestResult;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestResultActivity extends BaseActivity2 {
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private RiskTestResult riskTestResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        ButterKnife.bind(this);
        riskTestResult = (RiskTestResult) getIntent().getSerializableExtra("result");
        initView();
    }

    private void initView() {
    switch (riskTestResult.getRisklevel()){
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

    }


}
