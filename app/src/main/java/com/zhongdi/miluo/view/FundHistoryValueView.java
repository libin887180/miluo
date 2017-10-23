package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.HistoryValue;

import java.util.List;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface FundHistoryValueView extends BaseView {

    void dismissLoadingDialog();

    void showLoadingDialog();
    void  onDataSuccess(List<HistoryValue> data);
}
