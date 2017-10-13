package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.BeforeBuyInfo;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface BuyFundView extends BaseView {
    void disableSubmitBtn();

    void enableSubmitBtn();

    void onDataSuccess(BeforeBuyInfo body);
}
