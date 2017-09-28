package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.ViewPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Intent  intent  = new Intent(mContext, TestResultActivity.class);
        intent.putExtra("result",riskTestResult);
        startActivity(intent);
        finish();
    }

    @Override
    public void getQuestionSuccess(List<TestQuestion> body) {
        setupViewPager(viewPager, body);
    }
}
