package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface ForgetDealPsw2View extends BaseView {
    void enableSubmitBtn(boolean enable);

    void onSuccess();

    void reLogin();
}
