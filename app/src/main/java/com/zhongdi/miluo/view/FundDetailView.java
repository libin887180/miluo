package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.FundDetail;
import com.zhongdi.miluo.model.FundManagerInfo;
import com.zhongdi.miluo.model.FundNotice;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface FundDetailView extends BaseView {
    void OnDataSuccess(FundDetail body);
    void setListener();
    void OnFundManagerSuccess(FundManagerInfo managerInfo);
    void OnFundNoticeSuccess(FundNotice notice);
}
