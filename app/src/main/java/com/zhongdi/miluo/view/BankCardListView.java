package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.BankInfo;

import java.util.List;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface BankCardListView extends BaseView {
    void setupRefreshView();

    void setupStatusView();

    void setupHeadView();

    void onDataSuccess(List<BankInfo> body);

    void dismissLoadingDialog();

    void showLoadingDialog();

    void reLogin();
}
