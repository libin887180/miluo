package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.OptionalFund;

import java.util.List;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface CollectionView extends BaseView {

    void onDataSuccess(List<OptionalFund> data);
    void initInCreasePop();
    void initEmptyView();
    void initAnimation();
}
