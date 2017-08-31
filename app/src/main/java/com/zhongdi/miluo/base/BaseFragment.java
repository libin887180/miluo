package com.zhongdi.miluo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.zhongdi.miluo.MyApplication;

import butterknife.Unbinder;

/**
 * @ explain:
 * @ author：xujun on 2016-8-11 16:16
 * @ email：gdutxiaoxu@163.com
 */
public abstract class BaseFragment<P extends BasePresenter>  extends Fragment {
    protected P presenter;
    protected abstract P initPresenter();
    protected View rootView;
    protected MyApplication applica;
    protected Unbinder unbinder;
    /**
     * 表示View是否被初始化
     */
    protected boolean isViewInitiated;
    /**
     * 表示对用户是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 表示数据是否初始化
     */
    protected boolean isDataInitiated;
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding();
    }


    protected void binding(){
        applica = MyApplication.getInstance();
        presenter = initPresenter();
        mContext = getContext();
    }


    protected abstract void initView(View view);

    protected abstract int getLayoutId();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        initListener();
        initData();
        prepareFetchData();
    }

    protected void initListener() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    /***
     *
     * @param forceUpdate 表示是否在界面可见的时候是否强制刷新数据
     * @return
     */
    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            //  界面可见的时候再去加载数据
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) presenter.dettachView();
    }

    protected void initData() {
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null &&unbinder != Unbinder.EMPTY){
            unbinder.unbind();
        }
    }

}