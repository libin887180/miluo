package com.zhongdi.miluo.ui.fragment.fund;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.market.MainHoldAdapter;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.AssetAllocation;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.MainHold;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.widget.NOScollListView;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 * 投资组合
 */

public class FundCombinationFragment extends Fragment {
    Unbinder unbinder;
    View rootView;
    @BindView(R.id.chart1)
    PieChart mChart;
    @BindView(R.id.lv_main_hold)
    NOScollListView lvMainHold;
    MainHoldAdapter mainHoldAdapter;
    List<MainHold> datas = new ArrayList<>();
    @BindView(R.id.tv_zcg_date)
    TextView tvZcgDate;
    private String sellFundId;

    public static FundCombinationFragment newInstance(String info) {
        Bundle args = new Bundle();
        FundCombinationFragment fragment = new FundCombinationFragment();
        args.putString("sellFundId", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_fund_combination, null);
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
        initListView();
        getMainHold(sellFundId);
        getAssetAllocation(sellFundId);
    }

    private void getMainHold(String sellFundId) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId", sellFundId);
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_MAIN_HOLD, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<MainHold>>>() {
                    @Override
                    public void onSuccess(MResponse<List<MainHold>> response, int requestCode) {
                        datas.clear();
                        datas.addAll(response.getBody());
                        mainHoldAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailed(MResponse<List<MainHold>> response, int requestCode) {
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

    private void getAssetAllocation(String sellFundId) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId", sellFundId);
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_ZHANBI, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<AssetAllocation>>() {
                    @Override
                    public void onSuccess(MResponse<AssetAllocation> response, int requestCode) {
                        initPieChart(response.getBody());
                        tvZcgDate.setText(response.getBody().getReportDate());
                    }

                    @Override
                    public void onFailed(MResponse<AssetAllocation> response, int requestCode) {
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

    private void initListView() {
        lvMainHold.setFocusable(false);
        mainHoldAdapter = new MainHoldAdapter(getActivity());
        lvMainHold.setAdapter(mainHoldAdapter);
        mainHoldAdapter.setDataList(datas);
    }

    private void initPieChart(AssetAllocation allocation) {
        mChart.getDescription().setEnabled(false);
        mChart.setCenterText(generateCenterSpannableText("1213"));
        mChart.setCenterTextSize(10f);
        mChart.getDescription().setEnabled(false);
        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setTextSize(12f);
        l.setYEntrySpace(10f);
        l.setYOffset(0f);
        mChart.setDrawEntryLabels(false);
        mChart.setData(generatePieData(allocation));
    }

    protected PieData generatePieData(AssetAllocation allocation) {


        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();

        if (!TextUtils.isEmpty(allocation.getStockPercent())) {
            entries1.add(new PieEntry(Float.parseFloat(allocation.getStockPercent()), "股票占净比" + allocation.getStockPercent() + "%"));
        } else {
            entries1.add(new PieEntry(0, "债券占净比0.00%"));
        }
        if (!TextUtils.isEmpty(allocation.getDebtPercent())) {
            entries1.add(new PieEntry(Float.parseFloat(allocation.getDebtPercent()), "债券占净比" + allocation.getDebtPercent() + "%"));
        } else {
            entries1.add(new PieEntry(0, "债券占净比0.00%"));
        }
        if (!TextUtils.isEmpty(allocation.getOtherPercent())) {
            entries1.add(new PieEntry(Float.parseFloat(allocation.getOtherPercent()), "其他占净比" + allocation.getOtherPercent() + "%"));
        } else {
            entries1.add(new PieEntry(0, "其他占净比0.00%"));
        }
        PieDataSet ds1 = new PieDataSet(entries1, "");
        int[] VORDIPLOM_COLORS = {
                Color.parseColor("#ff2c40"), Color.parseColor("#fda54d"), Color.parseColor("#7e91f8"),
                Color.rgb(140, 234, 255), Color.rgb(255, 140, 157)
        };
        ds1.setColors(VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);
        ds1.setDrawValues(false);
        PieData d = new PieData(ds1);

        return d;
    }


    private SpannableString generateCenterSpannableText(String netAsset) {

        SpannableString s = new SpannableString(netAsset + "亿\n基金规模");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, netAsset.length() + 1, 0);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F51")), 0, netAsset.length() + 1, 0);
        s.setSpan(new RelativeSizeSpan(1f), netAsset.length() + 1, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), netAsset.length() + 1, s.length(), 0);
        return s;
    }


    @Override
    public void onDestroyView() {
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}