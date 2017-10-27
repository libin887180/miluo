package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.UserInfo;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface TiyanjinQuickLoginView extends BaseView {

    void loginSuccess(UserInfo userInfo);

    void disableLoginBtn();

    void enableLoginBtn();

}
