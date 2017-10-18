package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface OpenAccountView extends BaseView {
    void swapViewPagerTo(int index);

    void toOpenSuccess();
    void  showCodeDialog();
    void showCodeError();
    void showSendCode();
}
