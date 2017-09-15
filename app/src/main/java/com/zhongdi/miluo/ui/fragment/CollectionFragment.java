package com.zhongdi.miluo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.fingdo.statelayout.StateLayout;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhongdi.miluo.FragmentBackHandler;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.adapter.CollectionAdapter;
import com.zhongdi.miluo.adapter.market.IncreaseAdapter;
import com.zhongdi.miluo.base.BaseFragment;
import com.zhongdi.miluo.presenter.CollectionPresenter;
import com.zhongdi.miluo.ui.activity.SearchActivity;
import com.zhongdi.miluo.view.CollectionView;
import com.zhongdi.miluo.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zhongdi.miluo.R.id.lsvMore;

/**
 * Created by Administrator on 2017/7/24.
 */

public class CollectionFragment extends BaseFragment<CollectionPresenter> implements CollectionView, FragmentBackHandler {
    @BindView(R.id.rl_orther)
    LinearLayout rlOrther;
    @BindView(R.id.iv_increase)
    ImageView ivIncrease;
    Unbinder unbinder;
    RotateAnimation upAnimation;
    RotateAnimation downAnimation;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;

    private ListView lvIncrease;
    private PopupWindow increaseWindow;
    private IncreaseAdapter increaseAdapter;

    public static CollectionFragment newInstance(String info) {
        Bundle args = new Bundle();
        CollectionFragment fragment = new CollectionFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected CollectionPresenter initPresenter() {
        return new CollectionPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, rootView);//同样把 ButterKnife 抽出来

            initView(rootView);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，
            // 要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initListener() {
        super.initListener();


        lvIncrease.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                increaseAdapter.setCheck(i);
                increaseWindow.dismiss();

            }
        });
    }

    @Override
    protected void initView(View view) {
        initInCreasePop();
        initAnimation();
        initialize();
    }
    private void initialize() {

        List<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("bbb");
        strings.add("vvv");
        strings.add("ccc");
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        CollectionAdapter fundAdapter = new CollectionAdapter(getActivity(), strings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(fundAdapter);
        stateLayout.setUseAnimation(true);
//        stateLayout.setViewSwitchAnimProvider(new FadeScaleViewAnimProvider());
        stateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                Log.i("11", "刷新");
            }

            @Override
            public void loginClick() {
                Log.i("11", "登录");
            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void initAnimation() {

        upAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(100);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(100);
        downAnimation.setFillAfter(true);
    }

    @Override
    public void doSomething() {
    }


    @Override
    public void initInCreasePop() {
        View popView = View.inflate(mContext, R.layout.pop_win_layout, null);
        lvIncrease = (ListView) popView.findViewById(lsvMore);
        increaseAdapter = new IncreaseAdapter(mContext);
        lvIncrease.setAdapter(increaseAdapter);
        //创建PopupWindow对象，指定宽度和高度
        increaseWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popView.findViewById(R.id.gray_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseWindow.dismiss();
                ivIncrease.startAnimation(downAnimation);
            }
        });
        // 设置动画
        // sortWindow.setAnimationStyle(R.style.popup_window_anim);
        // 设置背景颜色
        // sortWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        // 设置可以获取焦点
        // increaseWindow.setFocusable(true);
        // 设置可以触摸弹出框以外的区域
        increaseWindow.setOutsideTouchable(false);
        increaseWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivIncrease.startAnimation(downAnimation);
            }
        });
        // TODO：更新popupwindow的状态
        increaseWindow.update();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_title_right, R.id.ll_increase})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_title_right:
                startActivity(new Intent(mContext, SearchActivity.class));

                break;
            case R.id.ll_increase:

                if (increaseWindow.isShowing()) {
                    increaseWindow.dismiss();
                } else {
                    ivIncrease.startAnimation(upAnimation);
                    increaseWindow.showAsDropDown(rlOrther);
                }

                break;
        }
    }


    @Override
    public boolean onBackPressed() {

        if (increaseWindow.isShowing()) {
            increaseWindow.dismiss();
            return true;
        }
        return false;

    }
}
