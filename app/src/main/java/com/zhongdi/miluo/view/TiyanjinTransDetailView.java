package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.FriendsInfo;
import com.zhongdi.miluo.model.TiyanjinDetail;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface TiyanjinTransDetailView extends BaseView {




    void dismissLoadingDialog();

    void showLoadingDialog();

    void OnDataSuccess(TiyanjinDetail body);

    void OnFriendsSuccess(FriendsInfo friendsInfo);

    void reLogin();
}
