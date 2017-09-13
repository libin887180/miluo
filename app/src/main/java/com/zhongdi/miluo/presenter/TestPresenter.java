package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.view.TestView;

/**
 * Created by libin on 2017/8/7.
 */

public class TestPresenter extends BasePresenter<TestView> {
    public TestPresenter(TestView view) {
        super.attachView(view);
    }

}
