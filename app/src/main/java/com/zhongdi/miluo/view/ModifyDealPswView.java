package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface ModifyDealPswView extends BaseView {

    void onSuccess();

    void onError(String message);
    void enableSubmitBtn(boolean enable);

    void reLogin();
}
