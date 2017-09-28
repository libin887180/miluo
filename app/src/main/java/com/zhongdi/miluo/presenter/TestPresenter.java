package com.zhongdi.miluo.presenter;

import com.zhongdi.miluo.base.BasePresenter;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.RiskTestResult;
import com.zhongdi.miluo.model.TestQuestion;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.view.TestView;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by libin on 2017/8/7.
 */

public class TestPresenter extends BasePresenter<TestView> {
    public TestPresenter(TestView view) {
        super.attachView(view);
    }

    public void getQuestions() {
        Map<String, String> map = new HashMap<>();
        view.showLoadingDialog();
        Callback.Cancelable post = netRequestUtil.post(URLConfig.RISK_TEST_QUESTION, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<TestQuestion>>>() {
                    @Override
                    public void onSuccess(MResponse<List<TestQuestion>> response, int requestCode) {
                        view.getQuestionSuccess(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<List<TestQuestion>> response, int requestCode) {
                        view.showToast(response.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {
                        view.dismissLoadingDialog();
                    }
                });
    }

    public void sendSelections(String resultStr) {
        Map<String, String> map = new HashMap<>();
        map.put("username", SpCacheUtil.getInstance().getLoginAccount());
        map.put("riskanswer", resultStr);
        view.showLoadingDialog();
        Callback.Cancelable post = netRequestUtil.post(URLConfig.RISK_SUBMIT, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<RiskTestResult>>() {
                    @Override
                    public void onSuccess(MResponse<RiskTestResult> response, int requestCode) {
                        view.toResultView(response.getBody());
                    }

                    @Override
                    public void onFailed(MResponse<RiskTestResult> response, int requestCode) {
                        view.showToast(response.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {
                        view.dismissLoadingDialog();
                    }
                });
    }

}
