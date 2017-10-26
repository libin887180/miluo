package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;

import java.util.List;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface ExchangeView extends BaseView {

    void onDataSuccess(List<String> body);

    void dismissLoadingDialog();

    void showLoadingDialog();

}
