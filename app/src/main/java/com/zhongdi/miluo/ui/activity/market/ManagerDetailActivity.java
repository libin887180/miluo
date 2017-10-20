package com.zhongdi.miluo.ui.activity.market;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.FundManagerInfo;
import com.zhongdi.miluo.presenter.ManagerDetailPresenter;
import com.zhongdi.miluo.util.StringUtil;
import com.zhongdi.miluo.util.xUtilsImageUtils;
import com.zhongdi.miluo.view.ManagerDetailView;

import butterknife.BindView;

import static com.zhongdi.miluo.R.id.tv_date;

public class ManagerDetailActivity extends BaseActivity<ManagerDetailPresenter> implements ManagerDetailView {

    @BindView(R.id.iv_head_img)
    ImageView ivHeadImg;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(tv_date)
    TextView tvDate;
    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.tv_return_rate)
    TextView tvReturnRate;
    @BindView(R.id.tv_resume)
    TextView tvResume;
    @BindView(R.id.tv_manager_name)
    TextView tvManagerName;
    private FundManagerInfo managerDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        managerDetail = (FundManagerInfo) getIntent().getSerializableExtra("managerDetail");
        binding(R.layout.activity_manager_detail);
    }

    @Override
    protected ManagerDetailPresenter initPresenter() {
        return new ManagerDetailPresenter(this);
    }

    @Override
    protected void initialize() {
        if (managerDetail != null) {
            xUtilsImageUtils.display(ivHeadImg, managerDetail.getIndiImgUrl(), true, R.drawable.head_default, R.drawable.head_default);
            tvManagerName.setText(managerDetail.getManagerName());
            if (TextUtils.isEmpty(managerDetail.getEndDate())) {
                tvDate.setText(managerDetail.getStartDate() + "至今");
            } else {
                tvDate.setText(managerDetail.getStartDate() + managerDetail.getEndDate());
            }

            tvReturnRate.setText(StringUtil.parseStr2Num(managerDetail.getRqhb()) + "%");
            tvResume.setText("        " + managerDetail.getResume());
        }
    }

}
