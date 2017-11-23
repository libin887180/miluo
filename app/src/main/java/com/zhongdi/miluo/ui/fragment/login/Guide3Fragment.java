package com.zhongdi.miluo.ui.fragment.login;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhongdi.miluo.R;
import com.zhongdi.miluo.cache.SpCacheUtil;
import com.zhongdi.miluo.ui.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class Guide3Fragment extends Fragment {
    Unbinder unbinder;
    private View rootView;

    public static Guide3Fragment newInstance() {
        Guide3Fragment fragment = new Guide3Fragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.guide_three, null);
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
        return rootView;
    }

    private void initialize() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
    }

    @OnClick(R.id.iv_tomain)
    public void onViewClicked() {
        PackageInfo info = null;
        try {
            info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            SpCacheUtil.getInstance().setIsFirstUse(SpCacheUtil.IS_FIRST_USE+info.versionCode,false);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();

    }
}