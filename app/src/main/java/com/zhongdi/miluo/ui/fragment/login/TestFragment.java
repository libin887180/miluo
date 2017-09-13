package com.zhongdi.miluo.ui.fragment.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.model.TestQuestion;
import com.zhongdi.miluo.ui.activity.login.TestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class TestFragment extends Fragment {
    Unbinder unbinder;
    View rootView;
    @BindView(R.id.tv_step)
    TextView tvStep;
    @BindView(R.id.tv_qusetion)
    TextView tvQusetion;
    @BindView(R.id.tv_selection_a)
    TextView tvSelectionA;
    @BindView(R.id.rl_a)
    RelativeLayout rlA;
    @BindView(R.id.tv_selection_b)
    TextView tvSelectionB;
    @BindView(R.id.rl_b)
    RelativeLayout rlB;
    @BindView(R.id.tv_selection_c)
    TextView tvSelectionC;
    @BindView(R.id.rl_c)
    RelativeLayout rlC;
    @BindView(R.id.tv_selection_d)
    TextView tvSelectionD;
    @BindView(R.id.rl_d)
    RelativeLayout rlD;
    @BindView(R.id.tv_pre)
    TextView tvPre;
    Unbinder unbinder1;
    int index;

    public static TestFragment newInstance(int info, TestQuestion data) {
        Bundle args = new Bundle();
        TestFragment fragment = new TestFragment();
        args.putInt("index", info);
        args.putSerializable("data", data);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_test, null);
            unbinder = ButterKnife.bind(this, rootView);
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
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initialize() {
        index = getArguments().getInt("index");
        TestQuestion data = (TestQuestion) getArguments().getSerializable("data");
        tvStep.setText(index + "");
        tvSelectionA.setText(data.getSelectionA());
        tvSelectionB.setText(data.getSelectionB());
        tvSelectionC.setText(data.getSelectionC());
        tvSelectionD.setText(data.getSelectionD());
        tvQusetion.setText(data.getQuestion());
        if (index > 1) {
            tvPre.setVisibility(View.VISIBLE);
        } else {
            tvPre.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
        super.onDestroyView();
        unbinder1.unbind();
    }

    @OnClick({R.id.rl_a, R.id.rl_b, R.id.rl_c, R.id.rl_d, R.id.tv_pre})
    public void onViewClicked(View view) {
        TestActivity activity = (TestActivity) getActivity();
        switch (view.getId()) {
            case R.id.rl_a:
                //设置条目背景色
                rlA.setBackground(getResources().getDrawable(R.drawable.title_bg));
                rlB.setBackgroundColor(Color.WHITE);
                rlC.setBackgroundColor(Color.WHITE);
                rlD.setBackgroundColor(Color.WHITE);
                //设置选中字颜色
                tvSelectionA.setTextColor(getResources().getColor(R.color.white));
                tvSelectionB.setTextColor(getResources().getColor(R.color.text_555));
                tvSelectionC.setTextColor(getResources().getColor(R.color.text_555));
                tvSelectionD.setTextColor(getResources().getColor(R.color.text_555));
                if (activity.result.size() < 10) {
                    activity.result.add("A");
                }
                if (index < 10) {
                    activity.getNext();
                } else {
                    sendSelections();
                }
                break;
            case R.id.rl_b:
                //设置条目背景色
                rlB.setBackground(getResources().getDrawable(R.drawable.title_bg));
                rlA.setBackgroundColor(Color.WHITE);
                rlC.setBackgroundColor(Color.WHITE);
                rlD.setBackgroundColor(Color.WHITE);
                //设置选中字颜色
                tvSelectionA.setTextColor(getResources().getColor(R.color.text_555));
                tvSelectionB.setTextColor(getResources().getColor(R.color.white));
                tvSelectionC.setTextColor(getResources().getColor(R.color.text_555));
                tvSelectionD.setTextColor(getResources().getColor(R.color.text_555));
                if (activity.result.size() < 10) {
                    activity.result.add("B");
                }
                if (index < 10) {
                    activity.getNext();
                } else {
                    sendSelections();
                }
                break;
            case R.id.rl_c:
                //设置条目背景色
                rlC.setBackground(getResources().getDrawable(R.drawable.title_bg));
                rlA.setBackgroundColor(Color.WHITE);
                rlB.setBackgroundColor(Color.WHITE);
                rlD.setBackgroundColor(Color.WHITE);
                tvSelectionA.setTextColor(getResources().getColor(R.color.text_555));
                tvSelectionB.setTextColor(getResources().getColor(R.color.text_555));
                tvSelectionC.setTextColor(getResources().getColor(R.color.white));
                tvSelectionD.setTextColor(getResources().getColor(R.color.text_555));
                if (activity.result.size() < 10) {
                    activity.result.add("C");
                }
                if (index < 10) {
                    activity.getNext();
                } else {
                    sendSelections();
                }

                break;
            case R.id.rl_d:
                //设置条目背景色
                rlD.setBackground(getResources().getDrawable(R.drawable.title_bg));
                rlA.setBackgroundColor(Color.WHITE);
                rlC.setBackgroundColor(Color.WHITE);
                rlB.setBackgroundColor(Color.WHITE);
                //设置选中字颜色
                tvSelectionA.setTextColor(getResources().getColor(R.color.text_555));
                tvSelectionB.setTextColor(getResources().getColor(R.color.text_555));
                tvSelectionC.setTextColor(getResources().getColor(R.color.text_555));
                tvSelectionD.setTextColor(getResources().getColor(R.color.white));
                if (activity.result.size() < 10) {
                    activity.result.add("D");
                }
                if (index < 10) {
                    activity.getNext();
                } else {
                    sendSelections();
                }
                break;
            case R.id.tv_pre:
                activity.result.remove(activity.result.size() - 1);
                activity.getPre();
                break;
        }
    }

    private void sendSelections() {
        ((TestActivity) getActivity()).sendSelections();

    }
}