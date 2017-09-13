package com.zhongdi.miluo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.ViewPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.model.TestQuestion;
import com.zhongdi.miluo.presenter.TestPresenter;
import com.zhongdi.miluo.ui.fragment.login.TestFragment;
import com.zhongdi.miluo.view.TestView;
import com.zhongdi.miluo.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TestActivity extends BaseActivity<TestPresenter> implements TestView {

    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;

    public List<String> result;

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
        setupViewPager(viewPager);
        result = new ArrayList<>();
    }

    private void setupViewPager(NoScrollViewPager viewPager) {
        viewPager.setScroll(false);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setQuestion("您的主要收入来源是您的主要收入来源是您的主要收入来源是?");
        testQuestion.setSelectionA("工资、劳务报酬");
        testQuestion.setSelectionB("生产经营所得您的主要收入来源是您的主要收入来源是");
        testQuestion.setSelectionC("利息、股息、转让等金融性资产收入您的主要收入来源是您的主要收入来源是您的主要收入来源是");
        testQuestion.setSelectionD("出租、出售房地产等非金融性资产收入");
        List<TestQuestion> testQuestions = new ArrayList<>();
        testQuestions.add(testQuestion);
        testQuestions.add(testQuestion);
        testQuestions.add(testQuestion);
        testQuestions.add(testQuestion);
        testQuestions.add(testQuestion);
        testQuestions.add(testQuestion);
        testQuestions.add(testQuestion);
        testQuestions.add(testQuestion);
        testQuestions.add(testQuestion);
        testQuestions.add(testQuestion);
        for (int i = 0; i < testQuestions.size(); i++) {
            adapter.addFragment(TestFragment.newInstance(i + 1, testQuestions.get(i)));
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
        startActivity(new Intent(mContext, TestResultActivity.class));
        finish();
    }
}
