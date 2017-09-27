package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface LoginView extends BaseView {
    void openMain();

    void openRegister();

    void findPsw();

    void loginSuccess();

    void onError(String message);

    void disableLoginBtn();

    void enableLoginBtn();

    void showErrorPswDialog();


}
