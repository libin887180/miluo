package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface ExchangeView extends BaseView {

    void onDataSuccess();

    void dismissLoadingDialog();

    void showLoadingDialog();

    void reLogin();
}
