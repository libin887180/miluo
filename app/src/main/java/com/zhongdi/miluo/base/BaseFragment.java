package com.zhongdi.miluo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhongdi.miluo.MyApplication;

/**
 * Created by libin on 2017/8/4.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {
    protected P presenter;

    protected abstract P initPresenter();

//    protected abstract void initialize();

    protected Context mContext;
    protected MyApplication applica;

    protected void binding() {
        mContext = getActivity();
        applica = MyApplication.getInstance();
        presenter = initPresenter();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) presenter.dettachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), "" + text, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int resId) {
        Toast.makeText(getActivity(), this.getResources().getText(resId), Toast.LENGTH_SHORT).show();
    }

    public void back(View targv) {
        getActivity().finish();
    }

}
