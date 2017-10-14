package com.zhongdi.miluo.presenter;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.InfomationNote;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.OptionalFundResponse;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.InfomationsView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class InfomationssPresenter extends BasePresenter<InfomationsView> {
    public InfomationssPresenter(InfomationsView view) {
        super.attachView(view);
    }


}
