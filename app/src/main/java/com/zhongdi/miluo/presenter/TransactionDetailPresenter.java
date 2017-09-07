package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.view.TransactionDetailView;

/**
 * Created by libin on 2017/8/7.
 */

public class TransactionDetailPresenter extends BasePresenter<TransactionDetailView> {
    public TransactionDetailPresenter(TransactionDetailView view) {
        super.attachView(view);
    }

}
