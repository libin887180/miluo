package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.DealRecord;
import com.zhongdi.miluo.model.PropertyDetail;

import java.util.List;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface TransactionDetailView extends BaseView {
    void setUpChart();

    void onTradRecordsSuccess(List<DealRecord> records);

    void onPropertySuccess(PropertyDetail body);
}
