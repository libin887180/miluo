package com.zhongdi.miluo.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.ViewPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.model.RiskTestResult;
import com.zhongdi.miluo.model.TestQuestion;
import com.zhongdi.miluo.presenter.TestPresenter;
import com.zhongdi.miluo.ui.fragment.login.TestFragment;
import com.zhongdi.miluo.util.StringUtil;
import com.zhongdi.miluo.view.TestView;
import com.zhongdi.miluo.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TestActivity extends BaseActivity<TestPresenter> implements TestView {

    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;

    public ArrayList<String> result;
    private int SOURCE ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SOURCE = getIntent().getIntExtra(IntentConfig.SOURCE, IntentConfig.SETTING);
        binding(R.layout.activity_test);
    }

    @Override
    protected TestPresenter initPresenter() {
        return new TestPresenter(this);
    }

    @Override
    protected void initialize() {

        result = new ArrayList<>();
        //获取问题列表
        presenter.getQuestions();
    }

    @Override
    public void setupViewPager(NoScrollViewPager viewPager, List<TestQuestion> questions) {
        viewPager.setScroll(false);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < questions.size(); i++) {
            adapter.addFragment(TestFragment.newInstance(i + 1, questions.get(i)));
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void reLogin(int code) {
        Intent intent  = new Intent(mContext, QuickLoginActivity.class);
        if(code==0){
            startActivityForResult(intent, 301);
        }else{
            startActivityForResult(intent, 302);
        }

    }


    public void getPre() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem > 0) {
            viewPager.setCurrentItem(currentItem - 1);
        }
    }

    public void getNext() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem < 11) {
            viewPager.setCurrentItem(currentItem + 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 301 && resultCode == Activity.RESULT_OK) {
            presenter.getQuestions();
        }
    }

    public void sendSelections() {
        String resultStr = StringUtil.join(result, ",");
        ViseLog.e(resultStr);
        presenter.sendSelections(resultStr);
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
    public void toResultView(RiskTestResult riskTestResult) {
        //保存SP中记录的评测登等级
        SpCacheUtil.getInstance().setUserTestLevel(riskTestResult.getRisklevel());
            Intent intent = new Intent(mContext, TestResultActivity.class);
            intent.putExtra("result", riskTestResult.getRisklevel());
            intent.putExtra(IntentConfig.SOURCE, SOURCE);
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
    }

    @Override
    public void getQuestionSuccess(List<TestQuestion> body) {
        setupViewPager(viewPager, body);
    }
}
