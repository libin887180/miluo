package com.zhongdi.miluo.ui.fragment.fund;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fingdo.statelayout.StateLayout;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
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
    @BindView(R.id.state_layout)
    StateLayout stateLayout;
    @BindView(R.id.lv_main_hold)
    NOScollListView lvMainHold;
    MainHoldAdapter mainHoldAdapter;
    List<MainHold> datas = new ArrayList<>();
    private String sellFundId;

    public static FundCombinationFragment newInstance(String info) {
        Bundle args = new Bundle();
        FundCombinationFragment fragment = new FundCombinationFragment();
        args.putString("info", info);
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
        getAssetAllocation("2217");
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
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterText(generateCenterSpannableText("0.12"));

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);


        setData(8, 100);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setDrawEntryLabels(false);
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);

    }
    protected String[] mParties = new String[] {
            "Party Aasdsadasdasd", "Party adsdasdasdasB", "Partdasdasdasdasy C", "Party D", "Party dasdasdE", "Partyasdasdasdasdasdasd F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };
    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    mParties[i % mParties.length],
                    getResources().getDrawable(R.drawable.ic_error)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
    private SpannableString generateCenterSpannableText(String netAsset) {

        SpannableString s = new SpannableString(netAsset + "亿\n基金规模");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 5, 0);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F51")), 0, 5, 0);
        s.setSpan(new RelativeSizeSpan(1f), 5, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 5, s.length(), 0);
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