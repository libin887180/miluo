package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.presenter.OpenAccoutPresenter;
import com.zhongdi.miluo.ui.fragment.login.OpenStep1Fragment;
import com.zhongdi.miluo.ui.fragment.login.OpenStep2Fragment;
import com.zhongdi.miluo.ui.fragment.login.OpenStep3Fragment;
import com.zhongdi.miluo.view.OpenAccountView;
import com.zhongdi.miluo.widget.CodeAlertDialog;
import com.zhongdi.miluo.widget.NoScrollViewPager;
import com.zhongdi.miluo.widget.StepView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class OpenAccountActivity extends BaseActivity<OpenAccoutPresenter> implements OpenAccountView {

    @BindView(R.id.step_view)
    StepView stepView;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    List<String> steps = Arrays.asList(new String[]{"个人信息", "交易密码", "绑定银行卡"});
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;
    private int currentIndex;
    public String bankcardno;
    public String bankno;
    public String identityno;
    public String name;
    public String phone;
    public String registryid;
    public String tradepwd;
    CodeAlertDialog codeAlertDialog;
    private int source;
    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            codeAlertDialog.getTxt_code().setText("验证码(" + millisUntilFinished / 1000 + "S)");
        }

        @Override
        public void onFinish() {
            codeAlertDialog.getTxt_code().setEnabled(true);
            codeAlertDialog.getTxt_code().setText("获取验证码");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        source = getIntent().getIntExtra(IntentConfig.SOURCE, -1);
        binding(R.layout.activity_open_account);
    }


    @Override
    protected OpenAccoutPresenter initPresenter() {
        return new OpenAccoutPresenter(this);
    }

    @Override
    public void dismissLoadingDialog() {
        getLoadingProgressDialog().dismiss();
    }

    @Override
    public void showLoadingDialog() {
        getLoadingProgressDialog().show();
    }

    @Override
    protected void initialize() {
        stepView.setSteps(steps);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(OpenStep1Fragment.newInstance("验证手机"));
        adapter.addFragment(OpenStep2Fragment.newInstance("设置密码"));
        adapter.addFragment(OpenStep3Fragment.newInstance("注册成功"));
        viewPager.setAdapter(adapter);
        viewPager.setScroll(false);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //切换返回 和上一步按钮
                if (position == 0) {
                    btnBack.setVisibility(View.VISIBLE);
                    tvTitleLeft.setVisibility(View.GONE);
                } else {
                    btnBack.setVisibility(View.GONE);
                    tvTitleLeft.setVisibility(View.VISIBLE);
                }
                stepView.selectedStep(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void showCodeDialog() {
        codeAlertDialog = new CodeAlertDialog(mContext).builder();
        codeAlertDialog.setTitle("短信验证码");
        codeAlertDialog.setMsg("输入尾号为" + phone.substring(phone.length() - 4, phone.length()) + "接收到的短信验证码");
        timer.start();
        codeAlertDialog.getTxt_code().setEnabled(false);
        codeAlertDialog.show();
        codeAlertDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        codeAlertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(codeAlertDialog.getEditCode().getText().toString())) {
                    showToast("请输入验证码");
                    return;
                }
//                openAccount();
                presenter.openAccountConfirm(codeAlertDialog.getEditCode().getText().toString());
            }
        });
        codeAlertDialog.setSendCodeText("发送验证码", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                presenter.resendMessage("");
                openAccount();
                timer.start();
                view.setEnabled(false);
            }
        });

//        codeAlertDialog.getEditCode().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                showSendCode();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        codeAlertDialog.show();
    }

    @Override
    public void showCodeError() {
        if (codeAlertDialog != null && codeAlertDialog.isShowing()) {
            codeAlertDialog.getTxt_msg().setTextColor(Color.RED);
            codeAlertDialog.getTxt_msg().setText("验证码错误或失效，请重新输入");
        }
    }

    @Override
    public void showSendCode() {
        if (codeAlertDialog != null && codeAlertDialog.isShowing()) {
            codeAlertDialog.getTxt_msg().setTextColor(getResources().getColor(R.color.text_555));
            codeAlertDialog.setMsg("输入尾号为" + phone.substring(phone.length() - 4, phone.length()) + "接收到的短信验证码");
        }
    }

    @Override
    public void swapViewPagerTo(int index) {
        currentIndex = index;
        viewPager.setCurrentItem(currentIndex);
    }

    @Override
    public void toOpenSuccess() {
        SpCacheUtil.getInstance().saveUserFundState();
        SpCacheUtil.getInstance().setBankCardCount("1");
        codeAlertDialog.dismiss();//关闭验证码对话框

        if (source == IntentConfig.TIYANJIN) {
            setResult(RESULT_OK);
            finish();
        } else {
            //到开户成功界面
            Intent intent = new Intent(mContext, OpenAccountSuccessActivity.class);
            intent.putExtra(IntentConfig.SOURCE, source);
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        }
    }

    @OnClick(R.id.tv_title_left)
    public void onViewClicked() {
        if (currentIndex > 0) {
            currentIndex = currentIndex - 1;
            viewPager.setCurrentItem(currentIndex);
        } else {
            finish();
        }
    }

    public void openAccount() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("bankcardno", bankcardno);
        requestMap.put("bankno", bankno);
        requestMap.put("identityno", identityno);
        requestMap.put("name", name);
        requestMap.put("phone", phone);
//        requestMap.put("registryid",registryid);
        requestMap.put("tradepwd", tradepwd);
        requestMap.put("source", source + "");
        presenter.openAccount(requestMap);

    }
}
