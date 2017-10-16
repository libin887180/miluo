package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.TradeRecord;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface TransactionRecordView extends BaseView {
    void OnDataSuccess(TradeRecord body);
}
