package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.FundType;

import java.util.List;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface MarketView extends BaseView {
    void doSomething();

    void initSortPop();
    void initInCreasePop();

    void initTabLayout(List<FundType> tabs);

    void initAnimation();
}
