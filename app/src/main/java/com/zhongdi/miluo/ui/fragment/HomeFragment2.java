package com.zhongdi.miluo.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.AwardedFundAdapter;
import com.zhongdi.miluo.adapter.DefaultAdapter;
import com.zhongdi.miluo.adapter.GridImageAdapter;
import com.zhongdi.miluo.adapter.HomeSpecialityAdapter;
import com.zhongdi.miluo.adapter.HotInvestmentAdapter;
import com.zhongdi.miluo.adapter.MiluoUnderstandAdapter;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.constants.IntentConfig;
import com.zhongdi.miluo.constants.MiluoConfig;
import com.zhongdi.miluo.constants.URLConfig;
import com.zhongdi.miluo.model.HomeActiv;
import com.zhongdi.miluo.model.HomeFund;
import com.zhongdi.miluo.model.HomeNews;
import com.zhongdi.miluo.model.HotSpots;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.model.MyProperty;
import com.zhongdi.miluo.model.NewComeBean;
import com.zhongdi.miluo.net.NetRequestUtil;
import com.zhongdi.miluo.ui.activity.MainActivity;
import com.zhongdi.miluo.ui.activity.SearchActivity;
import com.zhongdi.miluo.ui.activity.login.ChengxingActivity;
import com.zhongdi.miluo.ui.activity.login.FundStudyActivity;
import com.zhongdi.miluo.ui.activity.login.HotSpotsDetailActivity;
import com.zhongdi.miluo.ui.activity.login.InfomationsActivity;
import com.zhongdi.miluo.ui.activity.login.JuniorActivity;
import com.zhongdi.miluo.ui.activity.login.MessagesActivity;
import com.zhongdi.miluo.ui.activity.login.MiLuoPanActivity;
import com.zhongdi.miluo.ui.activity.login.OpenAccountActivity;
import com.zhongdi.miluo.ui.activity.login.QuickLoginActivity;
import com.zhongdi.miluo.ui.activity.login.SpecialActivity;
import com.zhongdi.miluo.ui.activity.login.TestActivity;
import com.zhongdi.miluo.ui.activity.login.TiyanjinInfoActivity;
import com.zhongdi.miluo.ui.activity.login.TiyanjinLoginActivity;
import com.zhongdi.miluo.ui.activity.market.FundCurrencyDetailActivity;
import com.zhongdi.miluo.ui.activity.market.FundDetailActivity;
import com.zhongdi.miluo.widget.MarqueeView;
import com.zhongdi.miluo.widget.MyRefreshView;
import com.zhongdi.miluo.widget.NoScrollGridView;
import com.zhongdi.miluo.widget.ObservableScrollView;
import com.zhongdi.miluo.widget.RiseNumberTextView;
import com.zhongdi.miluo.widget.reddot.IconDotTextView;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class HomeFragment2 extends Fragment implements ObservableScrollView.OnObservableScrollViewListener {
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.head)
    RelativeLayout head;
    @BindView(R.id.mScrollView)
    ObservableScrollView mScrollView;
    @BindView(R.id.recycler_view)
    RecyclerView hor_recyclerView;
    @BindView(R.id.recyclerView_hot)
    RecyclerView recyclerViewHot;
    @BindView(R.id.recyclerView_awarded)
    RecyclerView recyclerViewAwarded;
    @BindView(R.id.recyclerView_understand)
    RecyclerView recyclerViewUnderStand;
    @BindView(R.id.upview1)
    MarqueeView upview1;
    @BindView(R.id.gv_activity)
    NoScrollGridView gvActivity;
    @BindView(R.id.rl_login_state)
    RelativeLayout rlLoginState;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.tv_profit)
    TextView tvProfit;
    @BindView(R.id.tv_ex_porfit)
    TextView tvExPorfit;
    @BindView(R.id.tv_newer_largess)
    TextView tvNewerLargess;
    @BindView(R.id.btn_news)
    Button btnNews;
    @BindView(R.id.tvTotalAsset)
    RiseNumberTextView tvTotalAsset;
    @BindView(R.id.tv_yesteday_profits)
    RiseNumberTextView tvYestedayProfits;
    @BindView(R.id.cb_visable)
    CheckBox cbVisable;
    @BindView(R.id.img_msg)
    IconDotTextView imgMsg;
    @BindView(R.id.rl_scroll_news)
    RelativeLayout rlScrollNews;
    private View rootView;
    private List<HomeNews> scrollMsgs = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private MainActivity paraentActivity;
    private List<HomeActiv> activitys = new ArrayList<>();
    private List<HotSpots> hotSpots = new ArrayList<>();
    private List<HomeFund> dongnifunds = new ArrayList<>();
    private List<HomeFund> awardfunds = new ArrayList<>();
    private List<HomeFund> specialfunds = new ArrayList<>();
    private GridImageAdapter gridImageAdapter;
    MiluoUnderstandAdapter understandAdapter;
    HotInvestmentAdapter investmentAdapter;
    AwardedFundAdapter awardedFundAdapter;
    HomeSpecialityAdapter homeSpecialityAdapter;

    public static HomeFragment2 newInstance(String info) {
        Bundle args = new Bundle();
        HomeFragment2 fragment = new HomeFragment2();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.get("info").toString();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_2, null);
            paraentActivity = (MainActivity) getActivity();
            unbinder = ButterKnife.bind(this, rootView);
//            initData();
            initView();

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


    public void getProperty() {
        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.MY_PROPERTY, map, 108,
                new NetRequestUtil.NetResponseListener<MResponse<MyProperty>>() {
                    @Override
                    public void onSuccess(MResponse<MyProperty> response, int requestCode) {
                        if (MyApplication.getInstance().assetVisable) {
                            tvTotalAsset.withNumber(Double.parseDouble(response.getBody().getTotalasset()));
                            // 设置动画播放时间
                            tvTotalAsset.setDuration(1000);
                            // 开始播放动画
                            tvTotalAsset.start();

                            tvYestedayProfits.withNumber(Double.parseDouble(response.getBody().getDayincome()));
                            tvYestedayProfits.setDuration(1000);
                            // 开始播放动画
                            tvYestedayProfits.start();
                        } else {
                            cbVisable.setChecked(false);
                        }


                    }

                    @Override
                    public void onFailed(MResponse<MyProperty> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(paraentActivity, response.getMsg() + "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    private void setAssetVisable(boolean visable) {
        tvTotalAsset.setNumVisable(visable);
        tvYestedayProfits.setNumVisable(visable);
    }

    private void initView() {
        setupRefreshView();
        cbVisable.setEnabled(false);
        cbVisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean cheched) {
                MyApplication.getInstance().assetVisable = cheched;
                setAssetVisable(cheched);
            }
        });


        //特色基金列表
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hor_recyclerView.setFocusable(false);
        hor_recyclerView.setLayoutManager(layoutManager);
        homeSpecialityAdapter = new HomeSpecialityAdapter(getActivity(), specialfunds);
        hor_recyclerView.setAdapter(homeSpecialityAdapter);
        homeSpecialityAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<HomeFund>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, HomeFund item, int position) {

                Intent special = new Intent(getActivity(), SpecialActivity.class);
                special.putExtra("type", item.getType());
                startActivity(special);
            }
        });
        //投资热点
        recyclerViewHot.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerViewHot.setFocusable(false);
        investmentAdapter = new HotInvestmentAdapter(getActivity(), hotSpots);
        recyclerViewHot.setAdapter(investmentAdapter);
        investmentAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<HotSpots>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, HotSpots hot, int position) {
                Intent intent = new Intent(getActivity(), HotSpotsDetailActivity.class);
                intent.putExtra("type", hot.getType());
                startActivity(intent);
            }
        });
        //获奖基金列表
        awardedFundAdapter = new AwardedFundAdapter(getActivity(), awardfunds);
        awardedFundAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<HomeFund>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, HomeFund fund, int position) {
                Intent intent = new Intent(getActivity(), ChengxingActivity.class);
                intent.putExtra("type", fund.getType());
                intent.putExtra("code", fund.getFundCode());
                intent.putExtra("id", fund.getId());
                startActivity(intent);
            }
        });
        awardedFundAdapter.setOnItemChildClickListener(R.id.iv_collect, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isLogined) {
                    //添加到收藏
                    HomeFund tag = (HomeFund) v.getTag();
                    if (tag != null && tag.getStatus().equals("0")) {
                        collectFund(tag.getSellFundId());
                    } else if (tag != null && tag.getStatus().equals("1")) {
                        discollectFund(tag.getSellFundId());
                    }

                } else {
                    Intent intent = new Intent(getActivity(), QuickLoginActivity.class);
                    startActivityForResult(intent, 101);
                }

            }
        });
        recyclerViewAwarded.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        recyclerViewAwarded.setFocusable(false);
        recyclerViewAwarded.setAdapter(awardedFundAdapter);
        //米罗懂你列表
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        understandAdapter = new MiluoUnderstandAdapter(getActivity(), dongnifunds);
        understandAdapter.setOnItemClickListener(new DefaultAdapter.OnItemClickListener<HomeFund>() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, HomeFund fund, int position) {
                Intent intent;
                if (fund.getFundType().equals(MiluoConfig.HUOBI) || fund.getFundType().equals(MiluoConfig.DUANQI)) {
                    intent = new Intent(getActivity(), FundCurrencyDetailActivity.class);
                } else {
                    intent = new Intent(getActivity(), FundDetailActivity.class);
                }
                intent.putExtra("fundId", fund.getSellFundId());
                intent.putExtra("fundcode", fund.getFundCode());
                startActivity(intent);
            }
        });
        recyclerViewUnderStand.setLayoutManager(manager);
        recyclerViewUnderStand.setFocusable(false);
        recyclerViewUnderStand.setAdapter(understandAdapter);

        head.getBackground().setAlpha(0);
        mScrollView.setOnObservableScrollViewListener(this);

        gridImageAdapter = new GridImageAdapter(getActivity(), activitys);
        gvActivity.setAdapter(gridImageAdapter);
        gvActivity.setFocusable(false);
        gvActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (activitys.get(position).getType().equals("1")) {

                    if (!MyApplication.getInstance().isLogined) {
                        Intent login_tiyan = new Intent(getActivity(), TiyanjinLoginActivity.class);
                        startActivity(login_tiyan);
                    } else {
                        Intent buyIntent = new Intent(getActivity(), TiyanjinInfoActivity.class);
                        startActivity(buyIntent);
                    }

                } else if (activitys.get(position).getType().equals("5")) {
                    Intent study = new Intent(getActivity(), FundStudyActivity.class);
                    startActivity(study);
                } else if (activitys.get(position).getType().equals("3")) {
                    Intent miluo_pan = new Intent(getActivity(), MiLuoPanActivity.class);
                    startActivity(miluo_pan);

                }
            }
        });
        showHuodongDialog();
    }

    public void discollectFund(String fundId) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId", fundId);
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_DIS_COLLECT, map, 104,
                new NetRequestUtil.NetResponseListener<MResponse<Object>>() {
                    @Override
                    public void onSuccess(MResponse<Object> response, int requestCode) {
                        getAwardFunds();
                    }

                    @Override
                    public void onFailed(MResponse<Object> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(paraentActivity, response.getMsg() + "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    public void collectFund(String fundId) {
        Map<String, String> map = new HashMap<>();
        map.put("sellFundId", fundId);
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.FUND_COLLECT, map, 103,
                new NetRequestUtil.NetResponseListener<MResponse<Object>>() {
                    @Override
                    public void onSuccess(MResponse<Object> response, int requestCode) {
                        getAwardFunds();
                    }

                    @Override
                    public void onFailed(MResponse<Object> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(paraentActivity, response.getMsg() + "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    private void showHuodongDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.AlertDialogStyle);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_huodong, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        ImageView ivClose = (ImageView) dialogView.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        LinearLayout ll_open = (LinearLayout) dialogView.findViewById(R.id.ll_open);
        ll_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_tiyan = new Intent(getActivity(), TiyanjinLoginActivity.class);
                startActivity(login_tiyan);
                dialog.dismiss();
            }
        });
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.show();
    }

    private void setUpMarqueeView() {

        List<View> views2 = new ArrayList<>();
        views2.clear();//记得加这句话，不然可能会产生重影现象
        for (int i = 0; i < scrollMsgs.size(); i++) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) View.inflate(getActivity(), R.layout.item_view_single, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().startActivity(new Intent(getActivity(), InfomationsActivity.class));
                }
            });
            //进行对控件赋值
            tv1.setText(scrollMsgs.get(i).getArticletitle());
            //添加到循环滚动数组里面去
            views2.add(moreView);
        }

        upview1.setViews(views2);
    }

    private void initData() {
        if (MyApplication.getInstance().isLogined) {
            cbVisable.setEnabled(true);
            cbVisable.setChecked(MyApplication.getInstance().assetVisable);
            getProperty();
        }
        getScrollNews();
        getNewCommer();
        getActivs();
        gethotSpots();
        getMiluoUnderstand();
        getAwardFunds();
        getSpecialFund();
    }

    private void getScrollNews() {
        Map<String, String> map = new HashMap<>();
        map.put("articletag", "04,05,06");

        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.AUTO_NEWS, map, 101,
                new NetRequestUtil.NetResponseListener<MResponse<List<HomeNews>>>() {
                    @Override
                    public void onSuccess(MResponse<List<HomeNews>> response, int requestCode) {
                        scrollMsgs.clear();
                        scrollMsgs.addAll(response.getBody());
                        setUpMarqueeView();
                        if (scrollMsgs.size() == 0) {
                            rlScrollNews.setVisibility(View.GONE);
                        } else {
                            rlScrollNews.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailed(MResponse<List<HomeNews>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText(getActivity(), "onError", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinished() {
                    }
                });
    }

    private void getActivs() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.HOME_ACTIVE, map, 104,
                new NetRequestUtil.NetResponseListener<MResponse<List<HomeActiv>>>() {
                    @Override
                    public void onSuccess(MResponse<List<HomeActiv>> response, int requestCode) {
                        activitys.clear();
//                        for (int i = 0; i <2 ; i++) {
//                            activitys.add(response.getBody().get(i));
//                        }
                        activitys.addAll(response.getBody());
                        gridImageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailed(MResponse<List<HomeActiv>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText(getActivity(), "onError", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinished() {
                    }
                });
    }

    private void gethotSpots() {
        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.HOT_SPOTS, map, 105,
                new NetRequestUtil.NetResponseListener<MResponse<List<HotSpots>>>() {
                    @Override
                    public void onSuccess(MResponse<List<HotSpots>> response, int requestCode) {

                        hotSpots.clear();
                        hotSpots.addAll(response.getBody());
                        investmentAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailed(MResponse<List<HotSpots>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText(getActivity(), "onError", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinished() {
                    }
                });
    }


    private void getMiluoUnderstand() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "4");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.HOME_FUND, map, 105,
                new NetRequestUtil.NetResponseListener<MResponse<List<HomeFund>>>() {
                    @Override
                    public void onSuccess(MResponse<List<HomeFund>> response, int requestCode) {
                        dongnifunds.clear();
                        dongnifunds.addAll(response.getBody());
                        understandAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailed(MResponse<List<HomeFund>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText(getActivity(), "onError", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinished() {
                    }
                });
    }

    private void getAwardFunds() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "3");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.HOME_FUND, map, 105,
                new NetRequestUtil.NetResponseListener<MResponse<List<HomeFund>>>() {
                    @Override
                    public void onSuccess(MResponse<List<HomeFund>> response, int requestCode) {
                        awardfunds.clear();
                        awardfunds.addAll(response.getBody());
                        awardedFundAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailed(MResponse<List<HomeFund>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText(getActivity(), "onError", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinished() {
                    }
                });
    }

    private void getSpecialFund() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "2,8,9");
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.HOME_FUND, map, 105,
                new NetRequestUtil.NetResponseListener<MResponse<List<HomeFund>>>() {
                    @Override
                    public void onSuccess(MResponse<List<HomeFund>> response, int requestCode) {
                        specialfunds.clear();
                        specialfunds.addAll(response.getBody());
                        homeSpecialityAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailed(MResponse<List<HomeFund>> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText(getActivity(), "onError", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinished() {
                    }
                });
    }

    private void getNewCommer() {
        Map<String, String> map = new HashMap<>();
        Callback.Cancelable post = NetRequestUtil.getInstance().post(URLConfig.NEW_COMMER, map, 102,
                new NetRequestUtil.NetResponseListener<MResponse<NewComeBean>>() {
                    @Override
                    public void onSuccess(MResponse<NewComeBean> response, int requestCode) {
                        tvProfit.setText(response.getBody().getYearyld());
                        double exrate = Double.parseDouble(response.getBody().getExtrayearrate());

                        tvExPorfit.setText((int) exrate + "");
                        tvNewerLargess.setText((int) exrate + "%");
                        double min = Double.parseDouble(response.getBody().getMinsubscribeamt());
                        if (min < 1) {
                            btnNews.setText((int) (min * 100) + "分起购");
                        } else {
                            btnNews.setText((int) min + "元起购");
                        }

                    }

                    @Override
                    public void onFailed(MResponse<NewComeBean> response, int requestCode) {
                        ViseLog.e("请求失败");
                        Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText(getActivity(), "onError", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinished() {
                    }
                });
    }

    private void setupRefreshView() {
        MyRefreshView headerView = new MyRefreshView(getActivity());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        headerView.setOnPullListener(new MyRefreshView.OnPullListener() {
            @Override
            public void onPull(float fraction) {
                if (fraction > 0) {
                    head.setVisibility(View.GONE);
                } else {
                    head.setVisibility(View.VISIBLE);
                    head.getBackground().setAlpha(0);
                }
            }
        });
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                initData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
    }


    @Override
    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
        float scale;
        if (t > head.getHeight() * 2.5f) {
            scale = 1;
        } else {
            scale = t / (head.getHeight() * 2.5f);
        }
        float alpha = (255 * scale);
        if (alpha < 35) {
            alpha = 0;
        }

        head.getBackground().setAlpha((int) alpha);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getInstance().isLogined) {
            if (SpCacheUtil.getInstance().getUserFundState() == MiluoConfig.UN_OPEN_ACCOUNT) {
                rlLoginState.setVisibility(View.VISIBLE);
                btnLogin.setText("去开户");
            } else {
                if (SpCacheUtil.getInstance().getUserTestLevel() == -1) {
                    btnLogin.setText("去测评");
                    rlLoginState.setVisibility(View.VISIBLE);
                } else {
                    rlLoginState.setVisibility(View.GONE);
                }
            }
        } else {
            MyApplication.getInstance().assetVisable = false;
            cbVisable.setEnabled(false);
            cbVisable.setChecked(MyApplication.getInstance().assetVisable);
            rlLoginState.setVisibility(View.VISIBLE);
            btnLogin.setText("立即登录");
            setAssetVisable(false);
        }
//        if(SpCacheUtil.getInstance().get)
        imgMsg.setDotVisibility(false);
        initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initData();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK && requestCode == 102) {
//            if (MyApplication.getInstance().isLogined) {
//                if (SpCacheUtil.getInstance().getUserFundState() == MiluoConfig.UN_OPEN_ACCOUNT) {
//                    btnLogin.setText("去开户");
//                } else {
//                    if (SpCacheUtil.getInstance().getUserTestLevel() == -1) {
//                        btnLogin.setText("去测评");
//                    } else {
//                        rlLoginState.setVisibility(View.GONE);
//                    }
//                }
//            } else {
//                rlLoginState.setVisibility(View.VISIBLE);
//                btnLogin.setText("立即登录");
//            }
//        }
    }

    @OnClick({R.id.btn_login, R.id.img_msg, R.id.et_search, R.id.btn_news, R.id.ll_junior})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (btnLogin.getText().equals("立即登录")) {
                    Intent intent = new Intent(getActivity(), QuickLoginActivity.class);
                    intent.putExtra(IntentConfig.SOURCE, IntentConfig.HOME_LOGIN);
                    startActivityForResult(intent, 102);
                } else if (btnLogin.getText().equals("去开户")) {
                    Intent intent = new Intent(getActivity(), OpenAccountActivity.class);
                    intent.putExtra(IntentConfig.SOURCE, IntentConfig.HOME_LOGIN);
                    startActivityForResult(intent, 102);
                } else if (btnLogin.getText().equals("去测评")) {
                    Intent intent = new Intent(getActivity(), TestActivity.class);
                    intent.putExtra(IntentConfig.SOURCE, IntentConfig.HOME_LOGIN);
                    startActivityForResult(intent, 102);
                }

                break;
            case R.id.img_msg:

                if (MyApplication.getInstance().isLogined) {
                    startActivity(new Intent(getActivity(), MessagesActivity.class));
                } else {
                    Intent intent = new Intent(getActivity(), QuickLoginActivity.class);
                    startActivityForResult(intent, 101);
                }

                break;
            case R.id.et_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.btn_news:
                Intent intent = new Intent(MyApplication.getInstance(), JuniorActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_junior:
                Intent junior = new Intent(MyApplication.getInstance(), JuniorActivity.class);
                startActivity(junior);
                break;
        }
    }
}