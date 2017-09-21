package com.zhongdi.miluo.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.widget.AlertDialog;

import butterknife.ButterKnife;

/**
 * Created by isfaaghyth on 6/17/17.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected Context mContext;
    protected MyApplication applica;
    protected P presenter;

    protected abstract P initPresenter();

    protected abstract void initialize();

    protected void binding(int layoutId) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(layoutId);
        mContext = this;
        applica = MyApplication.getInstance();
        ButterKnife.bind(this);
        presenter = initPresenter();
        initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.dettachView();
    }

    public void showToast(String text) {
        Toast.makeText(this, "" + text, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int resId) {
        Toast.makeText(this, this.getResources().getText(resId), Toast.LENGTH_SHORT).show();
    }


    public void showDialog(String title, String messsage, String positiveMsg, View.OnClickListener okListener, String negative,
                           View.OnClickListener cancleListener) {

        AlertDialog dialog = new AlertDialog(mContext).builder();
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        if (!TextUtils.isEmpty(messsage)) {
            dialog.setMsg(messsage);
        }
        dialog.setPositiveButton(positiveMsg, okListener).setNegativeButton(negative, cancleListener);

        dialog.show();
    }


    public void back(View targv) {
        finish();
    }

}
