package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.RiskTestResult;
import com.zhongdi.miluo.model.TestQuestion;
import com.zhongdi.miluo.widget.NoScrollViewPager;

import java.util.List;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface TestView extends BaseView {
    void dismissLoadingDialog();
    void showLoadingDialog();
    void toResultView(RiskTestResult riskTestResult);
    void getQuestionSuccess(List<TestQuestion> body);
    void setupViewPager(NoScrollViewPager viewPager, List<TestQuestion> questions);

    void reLogin(int  code);
}
