package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.RateResponse;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface PremiumView extends BaseView {
    void onDateSuccess(RateResponse body);

}
