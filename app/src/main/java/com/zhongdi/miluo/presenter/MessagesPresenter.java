package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.view.MessagesView;

/**
 * Created by libin on 2017/8/7.
 */

public class MessagesPresenter extends BasePresenter<MessagesView> {
    public MessagesPresenter(MessagesView view) {
        super.attachView(view);
    }

}
