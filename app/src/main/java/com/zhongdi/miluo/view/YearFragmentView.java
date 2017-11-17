package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.FundValuationResponse;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface YearFragmentView extends BaseView {
    void onDataSuccess(FundValuationResponse response);
}
