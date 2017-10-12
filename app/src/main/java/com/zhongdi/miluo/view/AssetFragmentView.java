package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.HomeAssetBean;

import java.util.List;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface AssetFragmentView extends BaseView {
    void onDataSuccess(List<HomeAssetBean> body);
}
