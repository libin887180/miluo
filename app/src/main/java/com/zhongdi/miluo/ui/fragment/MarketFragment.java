package com.zhongdi.miluo.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseFragment;
import com.zhongdi.miluo.presenter.MarketPresenter;
import com.zhongdi.miluo.view.MarketView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MarketFragment extends BaseFragment<MarketPresenter> implements MarketView {
    Unbinder unbinder;
    @BindView(R.id.head)
    RelativeLayout head;
    private View rootView;
    private PopupWindow window;
    private List<String> datas;

    public static MarketFragment newInstance(String info) {
        Bundle args = new Bundle();
        MarketFragment fragment = new MarketFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MarketPresenter initPresenter() {
        return new MarketPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_market, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        binding();
        initSortPop(datas);
        return rootView;

    }

    @Override
    public void doSomething() {
    }

    @Override
    public void initSortPop(List<String> datas) {
        if(datas!=null){
            datas.clear();
        }else{
            datas = new ArrayList<>();
        }

        datas.add("默认");
        datas.add("正序");
        datas.add("倒序");
        // TODO: 2016/5/17 构建一个popupwindow的布局
        View popView = LayoutInflater.from(mContext).inflate(R.layout.pop_win_layout, null);
        ListView lsvMore = (ListView) popView.findViewById(R.id.lsvMore);
        lsvMore.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, datas));
        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
        window = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // TODO: 2016/5/17 设置动画
//        window.setAnimationStyle(R.style.popup_window_anim);
        // TODO: 2016/5/17 设置背景颜色
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        // TODO: 2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        window.update();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.tv_title_right)
    public void onViewClicked() {
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(head);
    }
}