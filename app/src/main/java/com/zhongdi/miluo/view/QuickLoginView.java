package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.Manager;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface QuickLoginView extends BaseView {
    void openMain();
    void onSuccess(Manager model);
    void onError(String message);
    void disableLoginBtn();
    void enableLoginBtn();

}
