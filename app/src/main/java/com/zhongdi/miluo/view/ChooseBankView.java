package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.BankInfo;

import java.util.List;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface ChooseBankView extends BaseView {

    void  onSuccess(List<BankInfo> bankList);
}
