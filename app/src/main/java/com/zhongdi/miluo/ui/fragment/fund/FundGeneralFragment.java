package com.zhongdi.miluo.ui.fragment.fund;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.FundGeneralAdapter;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.Archives;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.net.NetRequestUtil;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class FundGeneralFragment extends Fragment {
    Unbinder unbinder;
    View rootView;
    FundGeneralAdapter adapter;
    @BindView(R.id.tv_fund_name)
    TextView tvFundName;
    @BindView(R.id.tv_fund_code)
    TextView tvFundCode;
    @BindView(R.id.tv_fund_type)
    TextView tvFundType;
    @BindView(R.id.tv_estab_date)
    TextView tvEstabDate;
    @BindView(R.id.tv_min_fene)
    TextView tvMinFene;
    @BindView(R.id.tv_fund_size)
    TextView tvFundSize;
    @BindView(R.id.tv_manager_name)
    TextView tvManagerName;
    @BindView(R.id.tv_fund_custodian)
    TextView tvFundCustodian;
    @BindView(R.id.tv_manager)
    TextView tvManager;
    private String sellFundId;

    public static FundGeneralFragment newInstance(String info) {
        Bundle args = new Bundle();
        FundGeneralFragment fragment = new FundGeneralFragment();
        args.putString("sellFundId", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.layout_fund_general, null);
            unbinder = ButterKnife.bind(this, rootView);
            sellFundId = getArguments().getString("sellFundId");
            initialize();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，
            // 要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    private void initialize() {
        getfundSurvey(sellFundId);
    }

    private void getfundSurvey(String sellFundId) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId", sellFundId);
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_SURVEY, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<Archives>>() {
                    @Override
                    public void onSuccess(MResponse<Archives> response, int requestCode) {
                        tvFundName.setText(response.getBody().getFName());
                        tvFundCode.setText(response.getBody().getFundCode());
                        tvEstabDate.setText(response.getBody().getEstabDate());
                        tvMinFene.setText(response.getBody().getMinredemptionvol());
                        tvFundSize.setText(response.getBody().getFundSize());
                        tvManagerName.setText(response.getBody().getFundManagerName());
                        tvFundCustodian.setText(response.getBody().getCustodian());
                        tvManager.setText(response.getBody().getFundManager());
                        switch (response.getBody().getFundType()) {
                            case MiluoConfig.GUPIAO:
                                tvFundType.setText("股票型");
                                break;
                            case MiluoConfig.ZHAIQUAN:
                                tvFundType.setText("债券型");
                                break;
                            case MiluoConfig.HUNHE:
                                tvFundType.setText("混合型");
                                break;
                            case MiluoConfig.HUOBI:
                                tvFundType.setText("货币型");
                                break;
                            case MiluoConfig.ZHISHU:
                                tvFundType.setText("指数型");
                                break;
                            case MiluoConfig.BAOBEN:
                                tvFundType.setText("保本型");
                                break;
                            case MiluoConfig.ETF:
                                tvFundType.setText("ETF联接");
                                break;
                            case MiluoConfig.DQII:
                                tvFundType.setText("QDII");
                                break;
                            case MiluoConfig.LOF:
                                tvFundType.setText("LOF");
                                break;
                            case MiluoConfig.DUANQI:
                                tvFundType.setText("短期理财型");
                                break;
                            case MiluoConfig.ALL:
                                tvFundType.setText("全部");
                                break;
                            case MiluoConfig.ZUHE:
                                tvFundType.setText("组合型");
                                break;
                        }
                    }

                    @Override
                    public void onFailed(MResponse<Archives> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}