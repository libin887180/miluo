package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.FundNotice;

import java.util.List;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface FundNoticeView extends BaseView {
    void OnFundNoticeSuccess(List<FundNotice> body);

}
