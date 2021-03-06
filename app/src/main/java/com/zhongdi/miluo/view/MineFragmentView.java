package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.HomeAssetBean;
import com.zhongdi.miluo.model.MyProperty;

import java.util.List;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface MineFragmentView extends BaseView {


    void onDataSuccess(MyProperty property);

    void onCurrentPropertyList(List<HomeAssetBean> body);
    void onHisPropertyList(List<HomeAssetBean> body);

    void onRequestFinished();

    void reLogin();
}
