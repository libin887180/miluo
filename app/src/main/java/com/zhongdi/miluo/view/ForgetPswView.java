package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.Manager;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface ForgetPswView extends BaseView {
    void onSuccess(Manager model);

    void onError(String message);
}
