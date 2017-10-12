package com.zhongdi.miluo.view;

import android.support.v4.view.ViewPager;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.MyProperty;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface MineFragmentView extends BaseView {

    void setupViewPager(ViewPager viewPager);

    void onDataSuccess(MyProperty property);
}
