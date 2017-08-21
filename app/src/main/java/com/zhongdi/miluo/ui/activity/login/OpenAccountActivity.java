package com.zhongdi.miluo.ui.activity.login;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.MyFragmentPagerAdapter;
import com.zhongdi.miluo.base.BaseActivity;
import com.zhongdi.miluo.presenter.OpenAccoutPresenter;
import com.zhongdi.miluo.ui.fragment.login.OpenStep1Fragment;
import com.zhongdi.miluo.ui.fragment.login.OpenStep2Fragment;
import com.zhongdi.miluo.ui.fragment.login.OpenStep3Fragment;
import com.zhongdi.miluo.view.OpenAccountView;
import com.zhongdi.miluo.widget.NoScrollViewPager;
import com.zhongdi.miluo.widget.StepView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OpenAccountActivity extends BaseActivity<OpenAccoutPresenter> implements OpenAccountView {

    @BindView(R.id.step_view)
    StepView stepView;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    List<String> steps = Arrays.asList(new String[]{"验证手机", "设置密码", "注册成功"});
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_open_account);
    }


    @Override
    protected OpenAccoutPresenter initPresenter() {
        return new OpenAccoutPresenter(this);
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
                stepView.selectedStep(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void swapViewPagerTo(int index) {
        currentIndex =index;
        viewPager.setCurrentItem(currentIndex);
    }

    @OnClick(R.id.tv_title_left)
    public void onViewClicked() {
        if(currentIndex>0) {
            currentIndex = currentIndex - 1;
            viewPager.setCurrentItem(currentIndex);
        }else{
            finish();
        }
    }
}
