package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.view.MarketView;

/**
 * Created by libin on 2017/8/4.
 */

public class MarketPresenter extends BasePresenter<MarketView> {
    public MarketPresenter(MarketView view) {
        super.attachView(view);
    }

    /**
     * 账号格式验证
     */
    public  boolean isEmailValid(String email) {
        boolean flag = false;
        String[] accountArray = email.split("@");
        if (accountArray.length == 2 && !"".equals(accountArray[0]) && !"".equals
                (accountArray[1])) {
            flag = true;
            view.doSomething();
        }

        return flag;
    }


}
