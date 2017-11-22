package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.BeforeBuyInfo;
import com.zhongdi.miluo.model.BuyResponse;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface BuyFundView extends BaseView {
    void dismissLoadingDialog();

    void showLoadingDialog();

    void disableSubmitBtn();

    void enableSubmitBtn();

    void dismissPswPopWindow();

    void onDataSuccess(BeforeBuyInfo body);

    void onBuySuccess(BuyResponse body);
    void showTestDialog();
    void showReTestDialog();
    void showRiskTipDialog();

    void showPswLocked();

    void reLogin();
}
