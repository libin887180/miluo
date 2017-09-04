package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface RegistView extends BaseView {
    void openLogin();

    void disableLoginBtn();

    void enableLoginBtn();

    void onError(String message);

    void onSuccess();
}
