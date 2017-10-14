package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.BuyResponse;
import com.zhongdi.miluo.model.SellResponse;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface SellFundView extends BaseView {
    void disableSubmitBtn();

    void enableSubmitBtn();

    void onDataSuccess(SellResponse body);
    void dismissPswPopWindow();

    void dismissLoadingDialog();

    void showLoadingDialog();

    void onSellSuccess(BuyResponse body);
}
